package com.yavin.parcelonmap.presentation.create

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yavin.parcelonmap.ui.parcel.create.CreateParcelScreen

@Composable
fun CreateParcelRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CreateParcelViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CreateParcelScreen(
        state = uiState,
        onTrackingNumberChange = viewModel::onTrackingNumberChanged,
        onSaveClick = viewModel::onSaveClick,
        onBackClick = onBackClick,
        modifier = modifier
    )
}
