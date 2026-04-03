package com.yavin.myapplication

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.yavin.myapplication.di.AppContainer
import com.yavin.myapplication.navigation.AppNavHost
import com.yavin.myapplication.ui.theme.ParcelOnMapTheme

@Composable
fun ParcelOnMapApp() {
    val appContainer = remember { AppContainer() }

    ParcelOnMapTheme {
        AppNavHost(appContainer = appContainer)
    }
}
