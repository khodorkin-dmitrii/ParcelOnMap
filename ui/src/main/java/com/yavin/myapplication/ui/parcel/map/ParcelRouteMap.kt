package com.yavin.myapplication.ui.parcel.map

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.yavin.myapplication.ui.model.ParcelMapPointUiModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ParcelRouteMap(
    points: List<ParcelMapPointUiModel>,
    animationRequestId: Int,
    onAnimationRunningChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    if (points.isEmpty()) {
        return
    }

    val coordinates = points.map { LatLng(it.latitude, it.longitude) }
    val segmentProgress = remember(points) { Animatable(0f) }
    var completedSegmentCount by remember(points) { mutableStateOf(0) }
    var showFullRoute by remember(points) { mutableStateOf(true) }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            coordinates.last(),
            RouteCameraZoom
        )
    }
    val routePath = remember(coordinates, completedSegmentCount, segmentProgress.value, showFullRoute) {
        if (showFullRoute) {
            coordinates
        } else {
            buildList {
                add(coordinates.first())

                if (coordinates.size == 1) {
                    return@buildList
                }

                repeat(completedSegmentCount.coerceAtMost(coordinates.lastIndex)) { index ->
                    add(coordinates[index + 1])
                }

                val currentSegmentStartIndex = completedSegmentCount
                if (currentSegmentStartIndex < coordinates.lastIndex) {
                    add(
                        interpolateLatLng(
                            start = coordinates[currentSegmentStartIndex],
                            end = coordinates[currentSegmentStartIndex + 1],
                            progress = segmentProgress.value
                        )
                    )
                }
            }
        }
    }

    LaunchedEffect(coordinates) {
        completedSegmentCount = 0
        segmentProgress.snapTo(0f)
        showFullRoute = true
        onAnimationRunningChange(false)
        cameraPositionState.move(
            CameraUpdateFactory.newLatLngZoom(
                coordinates.last(),
                RouteCameraZoom
            )
        )
    }

    LaunchedEffect(animationRequestId, coordinates) {
        if (animationRequestId == 0 || coordinates.size < 2) {
            return@LaunchedEffect
        }

        onAnimationRunningChange(true)
        completedSegmentCount = 0
        segmentProgress.snapTo(0f)
        showFullRoute = false

        try {
            cameraPositionState.animate(
                update = CameraUpdateFactory.newLatLngZoom(
                    coordinates.first(),
                    RouteCameraZoom
                ),
                durationMs = InitialCameraMoveDurationMillis
            )

            for (index in 0 until coordinates.lastIndex) {
                segmentProgress.snapTo(0f)

                val cameraJob = launch {
                    delay(CameraSegmentStartDelayMillis)
                    cameraPositionState.animate(
                        update = CameraUpdateFactory.newLatLngZoom(
                            coordinates[index + 1],
                            RouteCameraZoom
                        ),
                        durationMs = RouteSegmentAnimationDurationMillis -
                            CameraSegmentStartDelayMillis.toInt()
                    )
                }

                segmentProgress.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(
                        durationMillis = RouteSegmentAnimationDurationMillis,
                        easing = LinearEasing
                    )
                )
                cameraJob.join()
                completedSegmentCount = index + 1
            }
        } finally {
            showFullRoute = true
            onAnimationRunningChange(false)
        }
    }

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState
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

private const val RouteCameraZoom = 5f
private const val RouteSegmentAnimationDurationMillis = 3000
private const val InitialCameraMoveDurationMillis = 1000
private const val CameraSegmentStartDelayMillis = 200L

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
