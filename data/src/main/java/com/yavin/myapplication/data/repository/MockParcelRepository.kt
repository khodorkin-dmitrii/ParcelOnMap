package com.yavin.myapplication.data.repository

import com.yavin.myapplication.data.model.Parcel
import com.yavin.myapplication.data.model.ParcelRoutePoint
import java.time.Instant

class MockParcelRepository : ParcelRepository {

    private val parcels = listOf(
        Parcel(
            id = "parcel-1",
            trackingNumber = "POM-001",
            status = "In transit",
            routePoints = listOf(
                ParcelRoutePoint("Belgrade", 44.7866, 20.4489, Instant.parse("2026-03-28T08:00:00Z")),
                ParcelRoutePoint("Budapest", 47.4979, 19.0402, Instant.parse("2026-03-29T11:30:00Z")),
                ParcelRoutePoint("Vienna", 48.2082, 16.3738, Instant.parse("2026-03-30T16:45:00Z"))
            )
        ),
        Parcel(
            id = "parcel-2",
            trackingNumber = "POM-002",
            status = "Sorting center",
            routePoints = listOf(
                ParcelRoutePoint("Prague", 50.0755, 14.4378, Instant.parse("2026-03-27T07:15:00Z")),
                ParcelRoutePoint("Brno", 49.1951, 16.6068, Instant.parse("2026-03-28T13:10:00Z"))
            )
        ),
        Parcel(
            id = "parcel-3",
            trackingNumber = "POM-003",
            status = "Label created",
            routePoints = listOf(
                ParcelRoutePoint("Sofia", 42.6977, 23.3219, Instant.parse("2026-03-31T09:20:00Z"))
            )
        )
    )

    override fun getParcels(): List<Parcel> = parcels

    override fun getParcel(parcelId: String): Parcel? = parcels.firstOrNull { it.id == parcelId }
}
