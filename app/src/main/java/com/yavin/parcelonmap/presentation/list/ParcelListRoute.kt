package com.yavin.parcelonmap.presentation.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yavin.parcelonmap.ui.parcel.list.ParcelListScreen

@Composable
fun ParcelListRoute(
    onParcelClick: (String) -> Unit,
    onSettingsClick: () -> Unit,
    onAddParcelClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ParcelListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ParcelListScreen(
        state = uiState,
        onParcelClick = onParcelClick,
        onSettingsClick = onSettingsClick,
        onAddParcelClick = onAddParcelClick,
        modifier = modifier
    )
}
