package com.yavin.parcelonmap.ui.settings

import com.yavin.parcelonmap.ui.theme.AppThemeMode

data class SettingsUiState(
    val themeMode: AppThemeMode = AppThemeMode.System,
    val isSampleDataImporting: Boolean = false
)
