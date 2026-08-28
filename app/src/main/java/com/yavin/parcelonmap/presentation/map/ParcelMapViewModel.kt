package com.yavin.parcelonmap.presentation.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yavin.parcelonmap.data.model.Parcel
import com.yavin.parcelonmap.data.repository.ParcelRepository
import com.yavin.parcelonmap.ui.parcel.map.ParcelMapCameraState
import com.yavin.parcelonmap.ui.parcel.map.ParcelMapPointUiModel
import com.yavin.parcelonmap.ui.parcel.map.ParcelMapUiState
import com.yavin.parcelonmap.ui.parcel.map.ParcelRouteReplayPhase
import com.yavin.parcelonmap.ui.parcel.map.ParcelRouteReplayState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@HiltViewModel(assistedFactory = ParcelMapViewModel.Factory::class)
class ParcelMapViewModel @AssistedInject constructor(
    repository: ParcelRepository,
    @Assisted private val parcelId: String
) : ViewModel() {

    private val formatter = DateTimeFormatter.ofPattern("dd MMM HH:mm")
    private val zoneId = ZoneId.systemDefault()
    private var routeReplayJob: Job? = null
    private val _uiState = MutableStateFlow(
        ParcelMapUiState(
            trackingNumber = "Loading",
            status = "Loading",
            points = emptyList(),
            emptyMessage = "Loading parcel route..."
        )
    )

    val uiState: StateFlow<ParcelMapUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.observeParcel(parcelId).collect { parcel ->
                _uiState.update { state ->
                    parcel?.toUiState(
                        replayState = state.replayState,
                        cameraState = state.cameraState
                    ) ?: state.copy(
                        trackingNumber = "Unknown parcel",
                        status = "Not found",
                        points = emptyList(),
                        emptyMessage = "Parcel route is unavailable."
                    )
                }
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(parcelId: String): ParcelMapViewModel
    }

    private fun Parcel.toUiState(
        replayState: ParcelRouteReplayState,
        cameraState: ParcelMapCameraState?
    ): ParcelMapUiState {
        val points = routePoints
            .sortedBy { it.timestamp }
            .map { point ->
                ParcelMapPointUiModel(
                    title = point.cityName,
                    latitude = point.latitude,
                    longitude = point.longitude,
                    timeLabel = point.timestamp.atZone(zoneId).format(formatter)
                )
            }

        return ParcelMapUiState(
            trackingNumber = trackingNumber,
            status = status,
            points = points,
            emptyMessage = "No route points available.",
            replayState = replayState,
            cameraState = cameraState
        )
    }

    fun onRouteReplayClick() {
        if (_uiState.value.replayState.isRunning || _uiState.value.points.size < 2) {
            return
        }

        routeReplayJob?.cancel()
        routeReplayJob = viewModelScope.launch {
            startRouteReplay()
        }
    }

    private suspend fun startRouteReplay() {
        setReplayState(
            ParcelRouteReplayState(
                phase = ParcelRouteReplayPhase.MovingToStart,
                showFullRoute = false
            )
        )
        delay(InitialCameraMoveDurationMillis)

        val segmentCount = _uiState.value.points.lastIndex
        for (segmentIndex in 0 until segmentCount) {
            runRouteSegment(segmentIndex)
        }

        setReplayState(
            ParcelRouteReplayState(
                phase = ParcelRouteReplayPhase.Finished,
                currentSegmentIndex = segmentCount,
                currentSegmentProgress = 1f,
                showFullRoute = true
            )
        )
    }

    private suspend fun runRouteSegment(segmentIndex: Int) {
        val startTimeNanos = System.nanoTime()

        while (true) {
            val elapsedMillis = (System.nanoTime() - startTimeNanos) / NanosInMillis
            val progress = (elapsedMillis.toFloat() / RouteSegmentAnimationDurationMillis)
                .coerceIn(0f, 1f)

            setReplayState(
                ParcelRouteReplayState(
                    phase = ParcelRouteReplayPhase.DrawingRoute,
                    currentSegmentIndex = segmentIndex,
                    currentSegmentProgress = progress,
                    showFullRoute = false
                )
            )

            if (progress >= 1f) {
                return
            }

            delay(RouteReplayFrameDelayMillis)
        }
    }

    fun onCameraPositionChanged(
        latitude: Double,
        longitude: Double,
        zoom: Float,
        bearing: Float,
        tilt: Float
    ) {
        val cameraState = ParcelMapCameraState(
            latitude = latitude,
            longitude = longitude,
            zoom = zoom,
            bearing = bearing,
            tilt = tilt
        )

        _uiState.update { state ->
            if (state.cameraState.isCloseTo(cameraState)) {
                state
            } else {
                state.copy(cameraState = cameraState)
            }
        }
    }

    private fun setReplayState(replayState: ParcelRouteReplayState) {
        _uiState.update { state ->
            state.copy(replayState = replayState)
        }
    }

}

private const val RouteSegmentAnimationDurationMillis = 3000L
private const val InitialCameraMoveDurationMillis = 1000L
private const val RouteReplayFrameDelayMillis = 16L
private const val NanosInMillis = 1_000_000L
private const val CoordinateEpsilon = 0.000001
private const val CameraFloatEpsilon = 0.01f

private fun ParcelMapCameraState?.isCloseTo(other: ParcelMapCameraState): Boolean {
    if (this == null) {
        return false
    }

    return kotlin.math.abs(latitude - other.latitude) < CoordinateEpsilon &&
        kotlin.math.abs(longitude - other.longitude) < CoordinateEpsilon &&
        kotlin.math.abs(zoom - other.zoom) < CameraFloatEpsilon &&
        kotlin.math.abs(bearing - other.bearing) < CameraFloatEpsilon &&
        kotlin.math.abs(tilt - other.tilt) < CameraFloatEpsilon
}
