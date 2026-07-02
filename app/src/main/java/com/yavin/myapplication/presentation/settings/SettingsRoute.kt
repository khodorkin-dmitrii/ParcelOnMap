package com.yavin.myapplication.presentation.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yavin.myapplication.ui.settings.SettingsScreen

@Composable
fun SettingsRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SettingsScreen(
        themeMode = uiState.themeMode,
        onThemeModeClick = viewModel::onThemeModeSelected,
        onBackClick = onBackClick,
        modifier = modifier
    )
}
