package com.yavin.myapplication.data.model

data class Parcel(
    val id: String,
    val trackingNumber: String,
    val status: String,
    val routePoints: List<ParcelRoutePoint>
)
