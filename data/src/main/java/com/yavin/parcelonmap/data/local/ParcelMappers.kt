package com.yavin.parcelonmap.data.local

import com.yavin.parcelonmap.data.model.Parcel
import com.yavin.parcelonmap.data.model.ParcelRoutePoint
import java.time.Instant

internal fun ParcelWithRoutePoints.toDomain(): Parcel = Parcel(
    id = parcel.id,
    trackingNumber = parcel.trackingNumber,
    status = parcel.status,
    routePoints = routePoints
        .sortedBy { it.routeOrder }
        .map { it.toDomain() }
)

private fun ParcelRoutePointEntity.toDomain(): ParcelRoutePoint = ParcelRoutePoint(
    cityName = cityName,
    latitude = latitude,
    longitude = longitude,
    timestamp = Instant.ofEpochMilli(timestampEpochMillis)
)

internal fun Parcel.toEntity(): ParcelEntity = ParcelEntity(
    id = id,
    trackingNumber = trackingNumber,
    status = status
)

internal fun Parcel.toRoutePointEntities(): List<ParcelRoutePointEntity> = routePoints
    .mapIndexed { index, point ->
        ParcelRoutePointEntity(
            parcelId = id,
            routeOrder = index,
            cityName = point.cityName,
            latitude = point.latitude,
            longitude = point.longitude,
            timestampEpochMillis = point.timestamp.toEpochMilli()
        )
    }
