package com.yavin.parcelonmap.presentation.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.material3.SnackbarHostState
import com.yavin.parcelonmap.ui.R
import com.yavin.parcelonmap.ui.settings.SettingsScreen

@Composable
fun SettingsRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val sampleDataLoadedMessage = stringResource(R.string.sample_data_loaded_message)
    val sampleDataLoadFailedMessage = stringResource(R.string.sample_data_load_failed_message)

    LaunchedEffect(viewModel) {
        viewModel.events.collect { event ->
            val message = when (event) {
                SettingsEvent.SampleDataImportSucceeded -> sampleDataLoadedMessage
                SettingsEvent.SampleDataImportFailed -> sampleDataLoadFailedMessage
            }
            snackbarHostState.showSnackbar(message)
        }
    }

    SettingsScreen(
        themeMode = uiState.themeMode,
        isSampleDataImporting = uiState.isSampleDataImporting,
        snackbarHostState = snackbarHostState,
        onThemeModeClick = viewModel::onThemeModeSelected,
        onLoadSampleDataClick = viewModel::onLoadSampleDataClick,
        onBackClick = onBackClick,
        modifier = modifier
    )
}
