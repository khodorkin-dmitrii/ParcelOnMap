package com.yavin.myapplication.data.repository

import org.junit.Assert.assertTrue
import org.junit.Test

class MockParcelRepositoryTest {

    private val repository = MockParcelRepository()

    @Test
    fun `each parcel has at least two route points in chronological order`() {
        repository.getParcels().forEach { parcel ->
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
