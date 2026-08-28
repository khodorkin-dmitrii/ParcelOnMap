package com.yavin.parcelonmap.ui.parcel.map

data class ParcelMapUiState(
    val trackingNumber: String,
    val status: String,
    val points: List<ParcelMapPointUiModel>,
    val emptyMessage: String,
    val replayState: ParcelRouteReplayState = ParcelRouteReplayState(),
    val cameraState: ParcelMapCameraState? = null
)

data class ParcelMapPointUiModel(
    val title: String,
    val latitude: Double,
    val longitude: Double,
    val timeLabel: String
)

data class ParcelMapCameraState(
    val latitude: Double,
    val longitude: Double,
    val zoom: Float,
    val bearing: Float = 0f,
    val tilt: Float = 0f
)

data class ParcelRouteReplayState(
    val phase: ParcelRouteReplayPhase = ParcelRouteReplayPhase.Idle,
    val currentSegmentIndex: Int = 0,
    val currentSegmentProgress: Float = 0f,
    val showFullRoute: Boolean = true
) {
    val isRunning: Boolean
        get() = phase == ParcelRouteReplayPhase.MovingToStart ||
            phase == ParcelRouteReplayPhase.DrawingRoute
}

enum class ParcelRouteReplayPhase {
    Idle,
    MovingToStart,
    DrawingRoute,
    Finished
}
