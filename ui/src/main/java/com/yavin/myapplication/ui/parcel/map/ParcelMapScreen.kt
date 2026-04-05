package com.yavin.myapplication.ui.parcel.map

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yavin.myapplication.ui.model.ParcelMapUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParcelMapScreen(
    state: ParcelMapUiState,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = state.trackingNumber)
                },
                navigationIcon = {
                    TextButton(onClick = onBackClick) {
                        Text(text = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = state.status,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            if (state.points.isEmpty()) {
                Text(
                    text = state.emptyMessage,
                    style = MaterialTheme.typography.bodyMedium
                )
            } else {
                ParcelRouteMap(
                    points = state.points,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            }
        }
    }
}
