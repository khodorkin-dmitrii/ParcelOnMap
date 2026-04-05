package com.yavin.myapplication.data.repository

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class MockParcelRepositoryTest {

    private val repository = MockParcelRepository()

    @Test
    fun `each parcel has route points in chronological order and ends in Belgrade`() {
        repository.getParcels().forEach { parcel ->
            assertFalse("Parcel ${parcel.id} should have at least one route point", parcel.routePoints.isEmpty())

            val timestamps = parcel.routePoints.map { it.timestamp }
            assertEquals(
                "Parcel ${parcel.id} should keep route points ordered by time",
                timestamps.sorted(),
                timestamps
            )

            val firstTimestamp = timestamps.first()
            val lastTimestamp = timestamps.last()
            assertTrue(
                "Parcel ${parcel.id} should end after it starts",
                lastTimestamp.isAfter(firstTimestamp)
            )
            assertEquals(
                "Belgrade, Serbia",
                parcel.routePoints.last().cityName
            )
        }
    }
}
