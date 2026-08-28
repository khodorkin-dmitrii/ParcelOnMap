package com.yavin.parcelonmap.data.local

import androidx.room.Embedded
import androidx.room.Relation

data class ParcelWithRoutePoints(
    @Embedded val parcel: ParcelEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "parcelId"
    )
    val routePoints: List<ParcelRoutePointEntity>
)
