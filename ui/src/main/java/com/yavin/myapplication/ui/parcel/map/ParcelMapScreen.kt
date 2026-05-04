package com.yavin.myapplication.ui.parcel.map

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.yavin.myapplication.ui.R
import com.yavin.myapplication.ui.model.ParcelMapUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParcelMapScreen(
    state: ParcelMapUiState,
    onBackClick: () -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val topAppBarColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)
    val topAppBarContentColor = MaterialTheme.colorScheme.onSurface

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = state.trackingNumber)
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = topAppBarColor,
                    scrolledContainerColor = topAppBarColor,
                    navigationIconContentColor = topAppBarContentColor,
                    titleContentColor = topAppBarContentColor,
                    actionIconContentColor = topAppBarContentColor
                ),
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = stringResource(R.string.back_content_description)
                        )
                    }
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
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            if (state.points.isNotEmpty()) {
                ParcelRouteMap(
                    points = state.points,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding)
                    .padding(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Column(
                    modifier = Modifier.padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = state.status,
                        style = MaterialTheme.typography.titleMedium
                    )

                    if (state.points.isEmpty()) {
                        Text(
                            text = state.emptyMessage,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}
