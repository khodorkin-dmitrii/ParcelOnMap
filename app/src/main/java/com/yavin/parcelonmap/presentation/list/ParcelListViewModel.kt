package com.yavin.parcelonmap.presentation.list

import androidx.lifecycle.ViewModel
import com.yavin.parcelonmap.data.model.Parcel
import com.yavin.parcelonmap.data.repository.ParcelRepository
import com.yavin.parcelonmap.ui.parcel.list.ParcelListItemUiModel
import com.yavin.parcelonmap.ui.parcel.list.ParcelListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import androidx.lifecycle.viewModelScope

@HiltViewModel
class ParcelListViewModel @Inject constructor(
    repository: ParcelRepository
) : ViewModel() {

    private val formatter = DateTimeFormatter.ofPattern("dd MMM HH:mm")
    private val zoneId = ZoneId.systemDefault()

    val uiState: StateFlow<ParcelListUiState> = repository.observeParcels()
        .map { parcels ->
            ParcelListUiState(
                parcels = parcels.map(::toUiModel),
                isLoading = false
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ParcelListUiState(isLoading = true)
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
