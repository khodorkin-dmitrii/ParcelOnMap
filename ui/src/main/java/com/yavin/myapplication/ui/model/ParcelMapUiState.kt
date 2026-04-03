package com.yavin.myapplication.ui.model

data class ParcelMapUiState(
    val trackingNumber: String,
    val status: String,
    val points: List<ParcelMapPointUiModel>,
    val emptyMessage: String
)
