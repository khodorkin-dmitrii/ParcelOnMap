package com.yavin.myapplication.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.yavin.myapplication.data.repository.ParcelRepository
import com.yavin.myapplication.ui.map.GoogleMapView
import com.yavin.myapplication.ui.theme.ParcelOnMapTheme

@Composable
fun ParcelOnMapApp(
    repository: ParcelRepository,
    modifier: Modifier = Modifier
) {
    ParcelOnMapTheme {
        Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
            GoogleMapView(
                modifier = Modifier.padding(innerPadding),
                parcel = repository.getParcels().first()
            )
        }
    }
}
