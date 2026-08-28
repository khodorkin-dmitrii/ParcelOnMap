package com.yavin.parcelonmap.presentation.map

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yavin.parcelonmap.ui.parcel.map.ParcelMapScreen

@Composable
fun ParcelMapRoute(
    parcelId: String,
    onBackClick: () -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel = hiltViewModel<ParcelMapViewModel, ParcelMapViewModel.Factory>(
        creationCallback = { factory -> factory.create(parcelId) }
    )
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ParcelMapScreen(
        state = uiState,
        onRouteReplayClick = viewModel::onRouteReplayClick,
        onCameraPositionChanged = viewModel::onCameraPositionChanged,
        onBackClick = onBackClick,
        onSettingsClick = onSettingsClick,
        modifier = modifier
    )
}
