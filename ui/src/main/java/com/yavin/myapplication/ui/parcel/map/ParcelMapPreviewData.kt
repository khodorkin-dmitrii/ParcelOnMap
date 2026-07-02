package com.yavin.myapplication.ui.parcel.map

object ParcelMapPreviewData {

    private val sampleRoutePoints = listOf(
        ParcelMapPointUiModel(
            title = "Lisbon",
            latitude = 38.7223,
            longitude = -9.1393,
            timeLabel = "03 Apr 08:15"
        ),
        ParcelMapPointUiModel(
            title = "Madrid",
            latitude = 40.4168,
            longitude = -3.7038,
            timeLabel = "03 Apr 11:40"
        ),
        ParcelMapPointUiModel(
            title = "Vienna",
            latitude = 48.2082,
            longitude = 16.3738,
            timeLabel = "03 Apr 18:40"
        ),
        ParcelMapPointUiModel(
            title = "Belgrade",
            latitude = 44.7866,
            longitude = 20.4489,
            timeLabel = "04 Apr 09:10"
        )
    )

    val content = ParcelMapUiState(
        trackingNumber = "POM-001",
        status = "In transit",
        points = sampleRoutePoints,
        emptyMessage = "No route points available."
    )

    val notFound = ParcelMapUiState(
        trackingNumber = "Unknown parcel",
        status = "Not found",
        points = emptyList(),
        emptyMessage = "Parcel route is unavailable."
    )

    val replay = content.copy(
        replayState = ParcelRouteReplayState(
            phase = ParcelRouteReplayPhase.DrawingRoute,
            currentSegmentIndex = 1,
            currentSegmentProgress = 0.45f,
            showFullRoute = false
        )
    )
}
