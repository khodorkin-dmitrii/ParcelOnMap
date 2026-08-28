package com.yavin.parcelonmap.data.model

import java.time.Instant

data class ParcelRoutePoint(
    val cityName: String,
    val latitude: Double,
    val longitude: Double,
    val timestamp: Instant
)
