package com.yavin.parcelonmap.ui.parcel.list

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yavin.parcelonmap.ui.R
import com.yavin.parcelonmap.ui.theme.ParcelOnMapTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParcelListScreen(
    state: ParcelListUiState,
    onParcelClick: (String) -> Unit,
    onSettingsClick: () -> Unit,
    onAddParcelClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.parcels_title))
                },
                actions = {
                    IconButton(onClick = onSettingsClick) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = stringResource(R.string.settings_content_description)
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddParcelClick) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_parcel_content_description)
                )
            }
        }
    ) { innerPadding ->
        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            state.parcels.isEmpty() -> {
                EmptyParcelList(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(24.dp)
                )
            }

            else -> {
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
    }
}

@Composable
private fun EmptyParcelList(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.empty_parcels_title),
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = stringResource(R.string.empty_parcels_message),
            style = MaterialTheme.typography.bodyMedium
        )
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
            state = ParcelListPreviewData.content,
            onParcelClick = {},
            onSettingsClick = {},
            onAddParcelClick = {}
        )
    }
}

@Preview(showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun ParcelListScreenNightPreview() {
    ParcelOnMapTheme {
        ParcelListScreen(
            state = ParcelListPreviewData.content,
            onParcelClick = {},
            onSettingsClick = {},
            onAddParcelClick = {}
        )
    }
}
