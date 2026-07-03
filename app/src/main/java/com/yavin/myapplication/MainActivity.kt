package com.yavin.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.yavin.myapplication.data.settings.AppSettingsRepository
import com.yavin.myapplication.ui.theme.AppThemeMode
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var appSettingsRepository: AppSettingsRepository
    private var initialThemeMode: AppThemeMode? by mutableStateOf(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { initialThemeMode == null }

        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            initialThemeMode = appSettingsRepository.themeMode.first()
        }

        enableEdgeToEdge()
        setContent {
            initialThemeMode?.let { themeMode ->
                ParcelOnMapApp(
                    appSettingsRepository = appSettingsRepository,
                    initialThemeMode = themeMode
                )
            }
        }
    }
}
