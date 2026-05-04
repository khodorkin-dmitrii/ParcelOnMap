package com.yavin.myapplication

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yavin.myapplication.di.AppContainer
import com.yavin.myapplication.navigation.AppNavHost
import com.yavin.myapplication.ui.theme.AppThemeMode
import com.yavin.myapplication.ui.theme.ParcelOnMapTheme

@Composable
fun ParcelOnMapApp() {
    val context = LocalContext.current
    val appContainer = remember(context) { AppContainer(context.applicationContext) }
    val themeMode = appContainer.appSettingsRepository.themeMode.collectAsStateWithLifecycle(
        initialValue = AppThemeMode.System
    )

    ParcelOnMapTheme(themeMode = themeMode.value) {
        AppNavHost(appContainer = appContainer)
    }
}
