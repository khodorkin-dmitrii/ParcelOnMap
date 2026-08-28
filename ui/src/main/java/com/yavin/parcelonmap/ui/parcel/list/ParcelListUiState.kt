package com.yavin.parcelonmap.ui.parcel.list

data class ParcelListUiState(
    val parcels: List<ParcelListItemUiModel> = emptyList(),
    val isLoading: Boolean = false
)
