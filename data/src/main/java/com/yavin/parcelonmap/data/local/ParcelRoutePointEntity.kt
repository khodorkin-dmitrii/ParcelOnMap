package com.yavin.parcelonmap.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "parcel_route_points",
    primaryKeys = ["parcelId", "routeOrder"],
    foreignKeys = [
        ForeignKey(
            entity = ParcelEntity::class,
            parentColumns = ["id"],
            childColumns = ["parcelId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["parcelId"])]
)
data class ParcelRoutePointEntity(
    val parcelId: String,
    val routeOrder: Int,
    val cityName: String,
    val latitude: Double,
    val longitude: Double,
    val timestampEpochMillis: Long
)
