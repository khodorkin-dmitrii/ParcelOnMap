package com.yavin.parcelonmap.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.yavin.parcelonmap.data.local.ParcelDao
import com.yavin.parcelonmap.data.local.ParcelDatabase
import com.yavin.parcelonmap.data.repository.ParcelRepository
import com.yavin.parcelonmap.data.repository.RoomParcelRepository
import com.yavin.parcelonmap.data.sample.ParcelMockDataFactory
import com.yavin.parcelonmap.data.settings.AppSettingsRepository
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
    fun provideParcelDatabase(
        @ApplicationContext context: Context
    ): ParcelDatabase = Room.databaseBuilder(
        context,
        ParcelDatabase::class.java,
        "parcel_on_map.db"
    ).build()

    @Provides
    fun provideParcelDao(
        database: ParcelDatabase
    ): ParcelDao = database.parcelDao()

    @Provides
    @Singleton
    fun provideParcelRepository(
        parcelDao: ParcelDao
    ): ParcelRepository = RoomParcelRepository(parcelDao)

    @Provides
    @Singleton
    fun provideParcelMockDataFactory(): ParcelMockDataFactory = ParcelMockDataFactory()

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
