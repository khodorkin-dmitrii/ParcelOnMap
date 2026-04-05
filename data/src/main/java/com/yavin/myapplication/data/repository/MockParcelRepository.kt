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
                ParcelRoutePoint("Lisbon, Portugal", 38.7223, -9.1393, Instant.parse("2026-03-24T07:20:00Z")),
                ParcelRoutePoint("Madrid, Spain", 40.4168, -3.7038, Instant.parse("2026-03-25T15:45:00Z")),
                ParcelRoutePoint("Milan, Italy", 45.4642, 9.1900, Instant.parse("2026-03-27T08:10:00Z")),
                ParcelRoutePoint("Budapest, Hungary", 47.4979, 19.0402, Instant.parse("2026-03-28T13:30:00Z")),
                ParcelRoutePoint("Belgrade, Serbia", 44.7866, 20.4489, Instant.parse("2026-03-29T09:15:00Z"))
            )
        ),
        Parcel(
            id = "parcel-2",
            trackingNumber = "POM-002",
            status = "Sorting center",
            routePoints = listOf(
                ParcelRoutePoint("New York, USA", 40.7128, -74.0060, Instant.parse("2026-03-23T10:00:00Z")),
                ParcelRoutePoint("Philadelphia, USA", 39.9526, -75.1652, Instant.parse("2026-03-23T18:40:00Z")),
                ParcelRoutePoint("Paris, France", 48.8566, 2.3522, Instant.parse("2026-03-25T11:50:00Z")),
                ParcelRoutePoint("Ljubljana, Slovenia", 46.0569, 14.5058, Instant.parse("2026-03-26T20:25:00Z")),
                ParcelRoutePoint("Belgrade, Serbia", 44.7866, 20.4489, Instant.parse("2026-03-27T08:55:00Z"))
            )
        ),
        Parcel(
            id = "parcel-3",
            trackingNumber = "POM-003",
            status = "Customs clearance",
            routePoints = listOf(
                ParcelRoutePoint("Seattle, USA", 47.6062, -122.3321, Instant.parse("2026-03-22T06:35:00Z")),
                ParcelRoutePoint("Anchorage, USA", 61.2181, -149.9003, Instant.parse("2026-03-22T19:10:00Z")),
                ParcelRoutePoint("Amsterdam, Netherlands", 52.3676, 4.9041, Instant.parse("2026-03-24T14:00:00Z")),
                ParcelRoutePoint("Vienna, Austria", 48.2082, 16.3738, Instant.parse("2026-03-25T17:30:00Z")),
                ParcelRoutePoint("Belgrade, Serbia", 44.7866, 20.4489, Instant.parse("2026-03-26T07:45:00Z"))
            )
        ),
        Parcel(
            id = "parcel-4",
            trackingNumber = "POM-004",
            status = "In transit",
            routePoints = listOf(
                ParcelRoutePoint("Shenzhen, China", 22.5431, 114.0579, Instant.parse("2026-03-21T05:50:00Z")),
                ParcelRoutePoint("Hong Kong, China", 22.3193, 114.1694, Instant.parse("2026-03-21T12:15:00Z")),
                ParcelRoutePoint("Dubai, UAE", 25.2048, 55.2708, Instant.parse("2026-03-22T22:40:00Z")),
                ParcelRoutePoint("Athens, Greece", 37.9838, 23.7275, Instant.parse("2026-03-24T09:35:00Z")),
                ParcelRoutePoint("Belgrade, Serbia", 44.7866, 20.4489, Instant.parse("2026-03-24T18:20:00Z"))
            )
        ),
        Parcel(
            id = "parcel-5",
            trackingNumber = "POM-005",
            status = "Arrived at destination hub",
            routePoints = listOf(
                ParcelRoutePoint("Shanghai, China", 31.2304, 121.4737, Instant.parse("2026-03-20T08:25:00Z")),
                ParcelRoutePoint("Urumqi, China", 43.8256, 87.6168, Instant.parse("2026-03-21T16:05:00Z")),
                ParcelRoutePoint("Istanbul, Turkey", 41.0082, 28.9784, Instant.parse("2026-03-23T07:40:00Z")),
                ParcelRoutePoint("Sofia, Bulgaria", 42.6977, 23.3219, Instant.parse("2026-03-23T19:30:00Z")),
                ParcelRoutePoint("Nis, Serbia", 43.3209, 21.8958, Instant.parse("2026-03-24T06:45:00Z")),
                ParcelRoutePoint("Belgrade, Serbia", 44.7866, 20.4489, Instant.parse("2026-03-24T11:10:00Z"))
            )
        ),
        Parcel(
            id = "parcel-6",
            trackingNumber = "POM-006",
            status = "Out for delivery",
            routePoints = listOf(
                ParcelRoutePoint("Beijing, China", 39.9042, 116.4074, Instant.parse("2026-03-19T04:10:00Z")),
                ParcelRoutePoint("Almaty, Kazakhstan", 43.2389, 76.8897, Instant.parse("2026-03-20T13:25:00Z")),
                ParcelRoutePoint("Budapest, Hungary", 47.4979, 19.0402, Instant.parse("2026-03-22T08:50:00Z")),
                ParcelRoutePoint("Novi Sad, Serbia", 45.2671, 19.8335, Instant.parse("2026-03-22T15:05:00Z")),
                ParcelRoutePoint("Belgrade, Serbia", 44.7866, 20.4489, Instant.parse("2026-03-22T18:45:00Z"))
            )
        )
    )

    override fun getParcels(): List<Parcel> = parcels

    override fun getParcel(parcelId: String): Parcel? = parcels.firstOrNull { it.id == parcelId }
}
