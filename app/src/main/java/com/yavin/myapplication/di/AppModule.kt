package com.yavin.myapplication.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.yavin.myapplication.data.repository.MockParcelRepository
import com.yavin.myapplication.data.repository.ParcelRepository
import com.yavin.myapplication.data.settings.AppSettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private val Context.appSettingsDataStore by preferencesDataStore(name = "app_settings")

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideParcelRepository(): ParcelRepository = MockParcelRepository()

    @Provides
    @Singleton
    fun provideAppSettingsDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> = context.appSettingsDataStore

    @Provides
    @Singleton
    fun provideAppSettingsRepository(
        dataStore: DataStore<Preferences>
    ): AppSettingsRepository = AppSettingsRepository(dataStore)
}
