package com.yavin.myapplication.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yavin.myapplication.data.model.Parcel
import com.yavin.myapplication.data.repository.ParcelRepository
import com.yavin.myapplication.ui.model.ParcelListItemUiModel
import com.yavin.myapplication.ui.model.ParcelListUiState
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class ParcelListViewModel(
    repository: ParcelRepository
) : ViewModel() {

    private val formatter = DateTimeFormatter.ofPattern("dd MMM HH:mm")
    private val zoneId = ZoneId.systemDefault()

    val uiState = ParcelListUiState(
        parcels = repository.getParcels().map(::toUiModel)
    )

    private fun toUiModel(parcel: Parcel): ParcelListItemUiModel {
        val sortedPoints = parcel.routePoints.sortedBy { it.timestamp }
        val firstPoint = sortedPoints.firstOrNull()
        val lastPoint = parcel.routePoints.maxByOrNull { it.timestamp }

        return ParcelListItemUiModel(
            id = parcel.id,
            trackingNumber = parcel.trackingNumber,
            fromCity = firstPoint?.cityName.orEmpty(),
            destinationCity = lastPoint?.cityName.orEmpty(),
            status = parcel.status,
            lastCity = lastPoint?.cityName.orEmpty(),
            lastUpdatedText = lastPoint?.timestamp
                ?.atZone(zoneId)
                ?.format(formatter)
                .orEmpty()
        )
    }

    companion object {
        fun factory(repository: ParcelRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ParcelListViewModel(repository) as T
                }
            }
    }
}
