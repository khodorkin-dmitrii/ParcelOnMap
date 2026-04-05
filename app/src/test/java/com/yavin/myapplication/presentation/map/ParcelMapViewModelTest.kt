package com.yavin.myapplication.presentation.map

import com.yavin.myapplication.data.model.Parcel
import com.yavin.myapplication.data.model.ParcelRoutePoint
import com.yavin.myapplication.data.repository.ParcelRepository
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.Instant

class ParcelMapViewModelTest {

    @Test
    fun `ui state sorts route points chronologically before rendering`() {
        val parcel = Parcel(
            id = "parcel-chronology",
            trackingNumber = "POM-TEST",
            status = "In transit",
            routePoints = listOf(
                ParcelRoutePoint("Belgrade, Serbia", 44.7866, 20.4489, Instant.parse("2026-03-29T09:15:00Z")),
                ParcelRoutePoint("Frankfurt, Germany", 50.1109, 8.6821, Instant.parse("2026-03-27T08:10:00Z")),
                ParcelRoutePoint("Los Angeles, USA", 34.0522, -118.2437, Instant.parse("2026-03-24T07:20:00Z"))
            )
        )
        val repository = object : ParcelRepository {
            override fun getParcels(): List<Parcel> = listOf(parcel)

            override fun getParcel(parcelId: String): Parcel? = parcel.takeIf { it.id == parcelId }
        }

        val uiState = ParcelMapViewModel(repository, parcel.id).uiState

        assertEquals(
            listOf("Los Angeles, USA", "Frankfurt, Germany", "Belgrade, Serbia"),
            uiState.points.map { it.title }
        )
    }
}
