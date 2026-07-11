package com.yavin.myapplication.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        ParcelEntity::class,
        ParcelRoutePointEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class ParcelDatabase : RoomDatabase() {

    abstract fun parcelDao(): ParcelDao
}
