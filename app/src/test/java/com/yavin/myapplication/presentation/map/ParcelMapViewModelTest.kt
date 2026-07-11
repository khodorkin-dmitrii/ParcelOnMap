package com.yavin.myapplication.presentation.map

import androidx.lifecycle.SavedStateHandle
import com.yavin.myapplication.data.model.Parcel
import com.yavin.myapplication.data.model.ParcelRoutePoint
import com.yavin.myapplication.data.repository.ParcelRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.Instant

class ParcelMapViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `ui state sorts route points chronologically before rendering`() = runTest {
        Dispatchers.setMain(StandardTestDispatcher(testScheduler))
        try {
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
                override fun observeParcels(): Flow<List<Parcel>> = flowOf(listOf(parcel))

                override fun observeParcel(parcelId: String): Flow<Parcel?> = flowOf(parcel.takeIf { it.id == parcelId })

                override suspend fun replaceParcels(parcels: List<Parcel>) = Unit
            }

            val savedStateHandle = SavedStateHandle(mapOf("parcelId" to parcel.id))
            val viewModel = ParcelMapViewModel(repository, savedStateHandle)
            advanceUntilIdle()

            assertEquals(
                listOf("Los Angeles, USA", "Frankfurt, Germany", "Belgrade, Serbia"),
                viewModel.uiState.value.points.map { it.title }
            )
        } finally {
            Dispatchers.resetMain()
        }
    }
}
