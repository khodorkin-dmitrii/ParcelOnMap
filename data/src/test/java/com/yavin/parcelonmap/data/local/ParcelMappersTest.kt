package com.yavin.parcelonmap.data.local

import com.yavin.parcelonmap.data.model.Parcel
import com.yavin.parcelonmap.data.model.ParcelRoutePoint
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.Instant

class ParcelMappersTest {

    @Test
    fun `route point timestamp is persisted as epoch milliseconds`() {
        val timestamp = Instant.parse("2026-03-24T07:20:00Z")
        val parcel = Parcel(
            id = "parcel-test",
            trackingNumber = "POM-TEST",
            status = "In transit",
            routePoints = listOf(
                ParcelRoutePoint(
                    cityName = "Lisbon, Portugal",
                    latitude = 38.7223,
                    longitude = -9.1393,
                    timestamp = timestamp
                )
            )
        )

        val routePointEntity = parcel.toRoutePointEntities().single()
        val restoredParcel = ParcelWithRoutePoints(
            parcel = parcel.toEntity(),
            routePoints = listOf(routePointEntity)
        ).toDomain()

        assertEquals(timestamp.toEpochMilli(), routePointEntity.timestampEpochMillis)
        assertEquals(timestamp, restoredParcel.routePoints.single().timestamp)
    }
}
