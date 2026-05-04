package com.yavin.myapplication.di

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.yavin.myapplication.data.settings.AppSettingsRepository
import com.yavin.myapplication.data.repository.MockParcelRepository
import com.yavin.myapplication.data.repository.ParcelRepository

private val Context.appSettingsDataStore by preferencesDataStore(name = "app_settings")

class AppContainer(
    context: Context,
    val parcelRepository: ParcelRepository = MockParcelRepository(),
    val appSettingsRepository: AppSettingsRepository = AppSettingsRepository(
        context.applicationContext.appSettingsDataStore
    )
)
