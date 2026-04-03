package com.yavin.myapplication.ui.map

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.yavin.myapplication.data.model.Parcel

@Composable
fun GoogleMapView(
    parcel: Parcel,
    modifier: Modifier = Modifier
) {
    val routePoints = parcel.routePoints.sortedBy { it.timestamp }
    val firstPoint = routePoints.first()
    val coordinates = routePoints.map { LatLng(it.latitude, it.longitude) }
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
        coordinates.forEachIndexed { index, coordinate ->
            val routePoint = routePoints[index]
            Marker(
                state = MarkerState(position = coordinate),
                title = routePoint.cityName,
                snippet = routePoint.timestamp.toString()
            )
        }

        if (coordinates.size > 1) {
            Polyline(points = coordinates)
        }
    }
}
