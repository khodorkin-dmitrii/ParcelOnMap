package com.yavin.myapplication.presentation.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yavin.myapplication.data.model.Parcel
import com.yavin.myapplication.data.repository.ParcelRepository
import com.yavin.myapplication.ui.model.ParcelMapPointUiModel
import com.yavin.myapplication.ui.model.ParcelMapUiState
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class ParcelMapViewModel(
    repository: ParcelRepository,
    parcelId: String
) : ViewModel() {

    private val formatter = DateTimeFormatter.ofPattern("dd MMM HH:mm")
    private val zoneId = ZoneId.systemDefault()

    val uiState = repository.getParcel(parcelId)?.let(::toUiState) ?: ParcelMapUiState(
        trackingNumber = "Unknown parcel",
        status = "Not found",
        points = emptyList(),
        emptyMessage = "Parcel route is unavailable."
    )

    private fun toUiState(parcel: Parcel): ParcelMapUiState {
        val points = parcel.routePoints
            .sortedBy { it.timestamp }
            .map { point ->
                ParcelMapPointUiModel(
                    title = point.cityName,
                    latitude = point.latitude,
                    longitude = point.longitude,
                    timeLabel = point.timestamp.atZone(zoneId).format(formatter)
                )
            }

        return ParcelMapUiState(
            trackingNumber = parcel.trackingNumber,
            status = parcel.status,
            points = points,
            emptyMessage = "No route points available."
        )
    }

    companion object {
        fun factory(
            repository: ParcelRepository,
            parcelId: String
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ParcelMapViewModel(repository, parcelId) as T
            }
        }
    }
}
