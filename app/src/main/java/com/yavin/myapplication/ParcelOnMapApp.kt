package com.yavin.myapplication

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yavin.myapplication.data.settings.AppSettingsRepository
import com.yavin.myapplication.navigation.AppNavHost
import com.yavin.myapplication.ui.theme.AppThemeMode
import com.yavin.myapplication.ui.theme.ParcelOnMapTheme

@Composable
fun ParcelOnMapApp(
    appSettingsRepository: AppSettingsRepository
) {
    val themeMode = appSettingsRepository.themeMode.collectAsStateWithLifecycle(
        initialValue = AppThemeMode.System
    )

    ParcelOnMapTheme(themeMode = themeMode.value) {
        AppNavHost()
    }
}
