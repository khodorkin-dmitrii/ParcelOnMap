package com.yavin.parcelonmap.ui.parcel.create

object CreateParcelPreviewData {

    val empty = CreateParcelUiState()

    val filled = CreateParcelUiState(
        trackingNumber = "POM-001"
    )

    val withMessage = CreateParcelUiState(
        trackingNumber = "POM-001",
        showNotImplementedMessage = true
    )
}
