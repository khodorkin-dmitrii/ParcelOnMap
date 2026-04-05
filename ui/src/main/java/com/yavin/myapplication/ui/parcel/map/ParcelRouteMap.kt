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
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.yavin.myapplication.ui.model.ParcelMapPointUiModel

@Composable
fun ParcelRouteMap(
    points: List<ParcelMapPointUiModel>,
    modifier: Modifier = Modifier
) {
    if (points.isEmpty()) {
        return
    }

    val firstPoint = points.first()
    val coordinates = points.map { LatLng(it.latitude, it.longitude) }
    val segmentProgress = remember(points) { Animatable(0f) }
    var completedSegmentCount by remember(points) { mutableStateOf(0) }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(firstPoint.latitude, firstPoint.longitude),
            5f
        )
    }
    val animatedPath = remember(coordinates, completedSegmentCount, segmentProgress.value) {
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

    LaunchedEffect(coordinates) {
        completedSegmentCount = 0
        segmentProgress.snapTo(0f)

        for (index in 0 until coordinates.lastIndex) {
            segmentProgress.snapTo(0f)
            segmentProgress.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = 3000,
                    easing = LinearEasing
                )
            )
            completedSegmentCount = index + 1
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

        if (animatedPath.size > 1) {
            Polyline(points = animatedPath)
        }
    }
}

private fun interpolateLatLng(
    start: LatLng,
    end: LatLng,
    progress: Float
): LatLng {
    val clampedProgress = progress.coerceIn(0f, 1f)
    val latitude = start.latitude + (end.latitude - start.latitude) * clampedProgress
    val longitude = start.longitude + (end.longitude - start.longitude) * clampedProgress
    return LatLng(latitude, longitude)
}
