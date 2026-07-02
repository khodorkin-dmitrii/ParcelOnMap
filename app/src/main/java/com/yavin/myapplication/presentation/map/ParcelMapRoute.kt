package com.yavin.myapplication.presentation.map

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yavin.myapplication.ui.parcel.map.ParcelMapScreen

@Composable
fun ParcelMapRoute(
    onBackClick: () -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ParcelMapViewModel = hiltViewModel()
) {
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
