package com.yavin.myapplication.ui.model

data class ParcelListItemUiModel(
    val id: String,
    val trackingNumber: String,
    val fromCity: String,
    val destinationCity: String,
    val status: String,
    val lastCity: String,
    val lastUpdatedText: String
)
