package com.yavin.myapplication

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yavin.myapplication.data.settings.AppSettingsRepository
import com.yavin.myapplication.navigation.AppNavHost
import com.yavin.myapplication.ui.theme.AppThemeMode
import com.yavin.myapplication.ui.theme.ParcelOnMapTheme

@Composable
fun ParcelOnMapApp(
    appSettingsRepository: AppSettingsRepository,
    initialThemeMode: AppThemeMode
) {
    val themeMode by appSettingsRepository.themeMode.collectAsStateWithLifecycle(
        initialValue = initialThemeMode
    )

    ParcelOnMapTheme(themeMode = themeMode) {
        AppNavHost()
    }
}
