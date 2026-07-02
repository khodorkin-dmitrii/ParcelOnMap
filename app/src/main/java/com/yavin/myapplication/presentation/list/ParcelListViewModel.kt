package com.yavin.myapplication.presentation.list

import androidx.lifecycle.ViewModel
import com.yavin.myapplication.data.model.Parcel
import com.yavin.myapplication.data.repository.ParcelRepository
import com.yavin.myapplication.ui.model.ParcelListItemUiModel
import com.yavin.myapplication.ui.model.ParcelListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class ParcelListViewModel @Inject constructor(
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

}
