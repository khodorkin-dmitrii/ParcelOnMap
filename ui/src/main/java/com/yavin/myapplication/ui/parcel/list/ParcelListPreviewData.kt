package com.yavin.myapplication.ui.parcel.list

object ParcelListPreviewData {

    val content = ParcelListUiState(
        parcels = listOf(
            ParcelListItemUiModel(
                id = "parcel-1",
                trackingNumber = "POM-001",
                fromCity = "Lisbon, Portugal",
                destinationCity = "Belgrade, Serbia",
                status = "In transit",
                lastCity = "Vienna",
                lastUpdatedText = "03 Apr 18:40"
            ),
            ParcelListItemUiModel(
                id = "parcel-2",
                trackingNumber = "POM-002",
                fromCity = "New York, USA",
                destinationCity = "Belgrade, Serbia",
                status = "Sorting center",
                lastCity = "Brno",
                lastUpdatedText = "03 Apr 14:15"
            ),
            ParcelListItemUiModel(
                id = "parcel-3",
                trackingNumber = "POM-003",
                fromCity = "Seattle, USA",
                destinationCity = "Belgrade, Serbia",
                status = "Label created",
                lastCity = "Sofia",
                lastUpdatedText = "03 Apr 09:20"
            )
        )
    )

    val empty = ParcelListUiState()
}
