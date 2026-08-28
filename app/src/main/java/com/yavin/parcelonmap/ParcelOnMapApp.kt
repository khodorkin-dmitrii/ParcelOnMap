package com.yavin.parcelonmap

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yavin.parcelonmap.data.settings.AppSettingsRepository
import com.yavin.parcelonmap.navigation.AppNavHost
import com.yavin.parcelonmap.ui.theme.AppThemeMode
import com.yavin.parcelonmap.ui.theme.ParcelOnMapTheme

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
