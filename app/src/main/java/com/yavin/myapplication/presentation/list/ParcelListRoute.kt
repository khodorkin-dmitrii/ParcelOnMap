package com.yavin.myapplication.presentation.list

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.yavin.myapplication.ui.parcel.list.ParcelListScreen

@Composable
fun ParcelListRoute(
    onParcelClick: (String) -> Unit,
    onSettingsClick: () -> Unit,
    onAddParcelClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ParcelListViewModel = hiltViewModel()
) {
    ParcelListScreen(
        state = viewModel.uiState,
        onParcelClick = onParcelClick,
        onSettingsClick = onSettingsClick,
        onAddParcelClick = onAddParcelClick,
        modifier = modifier
    )
}
