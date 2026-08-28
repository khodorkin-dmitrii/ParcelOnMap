package com.yavin.parcelonmap.data.sample

import org.junit.Assert.assertTrue
import org.junit.Test

class ParcelMockDataFactoryTest {

    private val factory = ParcelMockDataFactory()

    @Test
    fun `each parcel has at least two route points in chronological order`() {
        factory.createParcels().forEach { parcel ->
            assertTrue(
                "Parcel ${parcel.id} should have at least two route points",
                parcel.routePoints.size >= 2
            )

            assertTrue(
                "Parcel ${parcel.id} route points should be ordered by time",
                parcel.routePoints.zipWithNext().all { (prev, next) ->
                    !next.timestamp.isBefore(prev.timestamp)
                }
            )

            assertTrue(
                "Parcel ${parcel.id} should end after it starts",
                parcel.routePoints.last().timestamp.isAfter(parcel.routePoints.first().timestamp)
            )
        }
    }
}
