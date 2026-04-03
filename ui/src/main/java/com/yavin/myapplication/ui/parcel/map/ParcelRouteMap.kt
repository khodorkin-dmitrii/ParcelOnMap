package com.yavin.myapplication.ui.parcel.map

import androidx.compose.runtime.Composable
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
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(firstPoint.latitude, firstPoint.longitude),
            5f
        )
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

        if (coordinates.size > 1) {
            Polyline(points = coordinates)
        }
    }
}
