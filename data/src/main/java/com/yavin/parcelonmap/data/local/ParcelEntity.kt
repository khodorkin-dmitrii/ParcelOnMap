package com.yavin.parcelonmap.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "parcels")
data class ParcelEntity(
    @PrimaryKey val id: String,
    val trackingNumber: String,
    val status: String
)
