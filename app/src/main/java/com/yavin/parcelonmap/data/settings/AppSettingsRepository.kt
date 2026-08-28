package com.yavin.parcelonmap.data.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.yavin.parcelonmap.ui.theme.AppThemeMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AppSettingsRepository(
    private val dataStore: DataStore<Preferences>
) {
    val themeMode: Flow<AppThemeMode> = dataStore.data.map { preferences ->
        preferences[ThemeModeKey].toThemeMode()
    }

    suspend fun setThemeMode(mode: AppThemeMode) {
        dataStore.edit { preferences ->
            preferences[ThemeModeKey] = mode.toStorageValue()
        }
    }

    private fun String?.toThemeMode(): AppThemeMode {
        return when (this) {
            LightValue -> AppThemeMode.Light
            DarkValue -> AppThemeMode.Dark
            else -> AppThemeMode.System
        }
    }

    private fun AppThemeMode.toStorageValue(): String {
        return when (this) {
            AppThemeMode.System -> SystemValue
            AppThemeMode.Light -> LightValue
            AppThemeMode.Dark -> DarkValue
        }
    }

    private companion object {
        val ThemeModeKey = stringPreferencesKey("theme_mode")
        const val SystemValue = "system"
        const val LightValue = "light"
        const val DarkValue = "dark"
    }
}
