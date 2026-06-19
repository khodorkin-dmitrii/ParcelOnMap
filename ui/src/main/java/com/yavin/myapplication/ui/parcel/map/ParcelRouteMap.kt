package com.yavin.myapplication.ui.parcel.map

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.yavin.myapplication.ui.model.ParcelMapCameraState
import com.yavin.myapplication.ui.model.ParcelMapPointUiModel
import com.yavin.myapplication.ui.model.ParcelRouteReplayPhase
import com.yavin.myapplication.ui.model.ParcelRouteReplayState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@Composable
fun ParcelRouteMap(
    points: List<ParcelMapPointUiModel>,
    replayState: ParcelRouteReplayState,
    cameraState: ParcelMapCameraState?,
    onCameraPositionChanged: (
        latitude: Double,
        longitude: Double,
        zoom: Float,
        bearing: Float,
        tilt: Float
    ) -> Unit,
    modifier: Modifier = Modifier
) {
    if (points.isEmpty()) {
        return
    }

    val coordinates = remember(points) {
        points.map { LatLng(it.latitude, it.longitude) }
    }
    val initialCameraPosition = remember(coordinates) {
        cameraState?.toCameraPosition() ?: calculateReplayCameraPosition(
            coordinates = coordinates,
            replayState = replayState
        )
    }
    val cameraPositionState = rememberCameraPositionState {
        position = initialCameraPosition
    }
    val googleMapOptionsFactory = remember(initialCameraPosition) {
        {
            GoogleMapOptions().camera(initialCameraPosition)
        }
    }
    val routePath = remember(coordinates, replayState) {
        buildRoutePath(
            coordinates = coordinates,
            replayState = replayState
        )
    }
    var isCameraReadyToObserve by remember(cameraPositionState) {
        mutableStateOf(cameraState == null)
    }
    val shouldResumeReplayCameraOnStart = remember(cameraPositionState) {
        replayState.isRunning
    }
    val resumedReplaySegmentIndex = remember(cameraPositionState) {
        replayState.currentSegmentIndex.takeIf {
            replayState.phase == ParcelRouteReplayPhase.DrawingRoute
        }
    }

    LaunchedEffect(cameraPositionState, cameraState) {
        if (cameraState != null && !isCameraReadyToObserve) {
            cameraPositionState.move(
                CameraUpdateFactory.newCameraPosition(cameraState.toCameraPosition())
            )
            isCameraReadyToObserve = true
        }
    }

    LaunchedEffect(cameraPositionState, isCameraReadyToObserve) {
        if (!isCameraReadyToObserve) {
            return@LaunchedEffect
        }

        snapshotFlow { cameraPositionState.position }
            .map { position ->
                ParcelMapCameraState(
                    latitude = position.target.latitude,
                    longitude = position.target.longitude,
                    zoom = position.zoom,
                    bearing = position.bearing,
                    tilt = position.tilt
                )
            }
            .distinctUntilChanged()
            .collect { camera ->
                onCameraPositionChanged(
                    camera.latitude,
                    camera.longitude,
                    camera.zoom,
                    camera.bearing,
                    camera.tilt
                )
            }
    }

    LaunchedEffect(cameraPositionState, isCameraReadyToObserve) {
        if (!isCameraReadyToObserve) {
            return@LaunchedEffect
        }

        snapshotFlow { cameraPositionState.isMoving }
            .collect { isMoving ->
                if (!isMoving) {
                    val position = cameraPositionState.position
                    onCameraPositionChanged(
                        position.target.latitude,
                        position.target.longitude,
                        position.zoom,
                        position.bearing,
                        position.tilt
                    )
                }
            }
    }

    LaunchedEffect(coordinates, replayState.phase) {
        if (replayState.phase != ParcelRouteReplayPhase.MovingToStart || coordinates.size < 2) {
            return@LaunchedEffect
        }

        if (shouldResumeReplayCameraOnStart) {
            return@LaunchedEffect
        }

        cameraPositionState.animate(
            update = CameraUpdateFactory.newLatLngZoom(
                calculateReplayCameraTarget(
                    coordinates = coordinates,
                    replayState = replayState
                ),
                calculateReplayCameraZoom(
                    coordinates = coordinates,
                    replayState = replayState
                )
            ),
            durationMs = InitialCameraMoveDurationMillis
        )
    }

    LaunchedEffect(coordinates, replayState.phase, replayState.currentSegmentIndex) {
        if (replayState.phase != ParcelRouteReplayPhase.DrawingRoute || coordinates.size < 2) {
            return@LaunchedEffect
        }

        val segmentIndex = replayState.currentSegmentIndex.coerceIn(0, coordinates.lastIndex - 1)
        val isResumedSegment = segmentIndex == resumedReplaySegmentIndex &&
            shouldResumeReplayCameraOnStart
        val progressMillis =
            (replayState.currentSegmentProgress * RouteSegmentAnimationDurationMillis.toFloat()).toLong()
        val cameraDelayMillis = if (isResumedSegment) {
            0L
        } else {
            (CameraSegmentStartDelayMillis - progressMillis).coerceAtLeast(0L)
        }
        val cameraDurationMillis = (RouteSegmentAnimationDurationMillis - progressMillis - cameraDelayMillis)
            .coerceAtLeast(0L)
            .toInt()

        delay(cameraDelayMillis)
        cameraPositionState.animate(
            update = CameraUpdateFactory.newLatLngZoom(
                coordinates[segmentIndex + 1],
                calculateRouteCameraZoom(
                    start = coordinates[segmentIndex],
                    end = coordinates[segmentIndex + 1]
                )
            ),
            durationMs = cameraDurationMillis
        )
    }

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        googleMapOptionsFactory = googleMapOptionsFactory
    ) {
        points.forEachIndexed { index, point ->
            Marker(
                state = MarkerState(position = coordinates[index]),
                title = point.title,
                snippet = point.timeLabel
            )
        }

        if (routePath.size > 1) {
            Polyline(
                points = routePath,
                geodesic = true
            )
        }
    }
}

private const val SinglePointCameraZoom = 7f
private const val MinRouteCameraZoom = 2f
private const val MaxRouteCameraZoom = 12f
private const val RouteSegmentAnimationDurationMillis = 3000L
private const val InitialCameraMoveDurationMillis = 1000
private const val CameraSegmentStartDelayMillis = 200L

private fun ParcelMapCameraState.toCameraPosition(): CameraPosition {
    return CameraPosition(
        LatLng(latitude, longitude),
        zoom,
        tilt,
        bearing
    )
}

private fun calculateReplayCameraPosition(
    coordinates: List<LatLng>,
    replayState: ParcelRouteReplayState
): CameraPosition {
    return CameraPosition.fromLatLngZoom(
        calculateReplayCameraTarget(
            coordinates = coordinates,
            replayState = replayState
        ),
        calculateReplayCameraZoom(
            coordinates = coordinates,
            replayState = replayState
        )
    )
}

private fun buildRoutePath(
    coordinates: List<LatLng>,
    replayState: ParcelRouteReplayState
): List<LatLng> {
    if (replayState.showFullRoute || coordinates.size == 1) {
        return coordinates
    }

    if (replayState.phase == ParcelRouteReplayPhase.MovingToStart) {
        return listOf(coordinates.first())
    }

    if (replayState.phase != ParcelRouteReplayPhase.DrawingRoute) {
        return coordinates
    }

    return buildList {
        add(coordinates.first())

        val currentSegmentIndex = replayState.currentSegmentIndex
            .coerceIn(0, coordinates.lastIndex - 1)

        repeat(currentSegmentIndex) { index ->
            add(coordinates[index + 1])
        }

        add(
            interpolateLatLng(
                start = coordinates[currentSegmentIndex],
                end = coordinates[currentSegmentIndex + 1],
                progress = replayState.currentSegmentProgress
            )
        )
    }
}

private fun calculateReplayCameraTarget(
    coordinates: List<LatLng>,
    replayState: ParcelRouteReplayState
): LatLng {
    if (!replayState.isRunning || coordinates.size == 1) {
        return coordinates.last()
    }

    if (replayState.phase == ParcelRouteReplayPhase.MovingToStart) {
        return coordinates.first()
    }

    val currentSegmentIndex = replayState.currentSegmentIndex
        .coerceIn(0, coordinates.lastIndex - 1)

    return coordinates[currentSegmentIndex + 1]
}

private fun calculateReplayCameraZoom(
    coordinates: List<LatLng>,
    replayState: ParcelRouteReplayState
): Float {
    if (!replayState.isRunning || coordinates.size == 1) {
        return calculateInitialCameraZoom(coordinates)
    }

    val currentSegmentIndex = replayState.currentSegmentIndex
        .coerceIn(0, coordinates.lastIndex - 1)

    return calculateRouteCameraZoom(
        start = coordinates[currentSegmentIndex],
        end = coordinates[currentSegmentIndex + 1]
    )
}

private fun calculateInitialCameraZoom(
    coordinates: List<LatLng>
): Float {
    return if (coordinates.size == 1) {
        SinglePointCameraZoom
    } else {
        calculateRouteCameraZoom(
            start = coordinates[coordinates.lastIndex - 1],
            end = coordinates.last()
        )
    }
}

private fun calculateRouteCameraZoom(
    start: LatLng,
    end: LatLng
): Float {
    val distanceMeters = SphericalUtil.computeDistanceBetween(start, end)
    val distanceKm = distanceMeters / 1000.0

    return when {
        distanceKm < 25 -> 12f
        distanceKm < 75 -> 10f
        distanceKm < 150 -> 7f
        distanceKm < 300 -> 6.5f
        distanceKm < 700 -> 5f
        distanceKm < 1500 -> 4.5f
        distanceKm < 3000 -> 4f
        distanceKm < 6000 -> 2f
        else -> 2f
    }.coerceIn(MinRouteCameraZoom, MaxRouteCameraZoom)
}

private fun interpolateLatLng(
    start: LatLng,
    end: LatLng,
    progress: Float
): LatLng {
    return SphericalUtil.interpolate(
        start,
        end,
        progress.coerceIn(0f, 1f).toDouble()
    )
}
