package com.yavin.myapplication.ui.parcel.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yavin.myapplication.ui.model.ParcelListItemUiModel
import com.yavin.myapplication.ui.model.ParcelListUiState
import com.yavin.myapplication.ui.theme.ParcelOnMapTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParcelListScreen(
    state: ParcelListUiState,
    onParcelClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Parcels")
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = state.parcels,
                key = { it.id }
            ) { parcel ->
                ParcelListItem(
                    parcel = parcel,
                    onClick = { onParcelClick(parcel.id) }
                )
            }
        }
    }
}

@Composable
private fun ParcelListItem(
    parcel: ParcelListItemUiModel,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = parcel.trackingNumber,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "From ${parcel.fromCity} - to ${parcel.destinationCity}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Last city: ${parcel.lastCity}",
                style = MaterialTheme.typography.bodySmall
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = parcel.status,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Updated: ${parcel.lastUpdatedText}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ParcelListScreenPreview() {
    ParcelOnMapTheme {
        ParcelListScreen(
            state = ParcelListUiState(
                parcels = listOf(
                    ParcelListItemUiModel(
                        id = "parcel-1",
                        trackingNumber = "POM-001",
                        fromCity = "Lisbon, Portugal",
                        destinationCity = "Belgrade, Serbia",
                        status = "In transit",
                        lastCity = "Vienna",
                        lastUpdatedText = "03 Apr 18:40"
                    ),
                    ParcelListItemUiModel(
                        id = "parcel-2",
                        trackingNumber = "POM-002",
                        fromCity = "New York, USA",
                        destinationCity = "Belgrade, Serbia",
                        status = "Sorting center",
                        lastCity = "Brno",
                        lastUpdatedText = "03 Apr 14:15"
                    ),
                    ParcelListItemUiModel(
                        id = "parcel-3",
                        trackingNumber = "POM-003",
                        fromCity = "Seattle, USA",
                        destinationCity = "Belgrade, Serbia",
                        status = "Label created",
                        lastCity = "Sofia",
                        lastUpdatedText = "03 Apr 09:20"
                    )
                )
            ),
            onParcelClick = {}
        )
    }
}
