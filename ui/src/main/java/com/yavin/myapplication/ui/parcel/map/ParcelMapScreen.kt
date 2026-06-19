package com.yavin.myapplication.ui.parcel.map

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yavin.myapplication.ui.R
import com.yavin.myapplication.ui.model.ParcelMapUiState
import com.yavin.myapplication.ui.theme.ParcelOnMapTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParcelMapScreen(
    state: ParcelMapUiState,
    onRouteReplayClick: () -> Unit,
    onCameraPositionChanged: (
        latitude: Double,
        longitude: Double,
        zoom: Float,
        bearing: Float,
        tilt: Float
    ) -> Unit,
    onBackClick: () -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val topAppBarColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)
    val topAppBarContentColor = MaterialTheme.colorScheme.onSurface
    val replayState = state.replayState

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = state.trackingNumber)
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = topAppBarColor,
                    scrolledContainerColor = topAppBarColor,
                    navigationIconContentColor = topAppBarContentColor,
                    titleContentColor = topAppBarContentColor,
                    actionIconContentColor = topAppBarContentColor
                ),
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = stringResource(R.string.back_content_description)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onSettingsClick) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = stringResource(R.string.settings_content_description)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            if (state.points.isNotEmpty()) {
                ParcelRouteMap(
                    points = state.points,
                    replayState = replayState,
                    cameraState = state.cameraState,
                    onCameraPositionChanged = onCameraPositionChanged,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Card(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Column(
                    modifier = Modifier.padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = state.status,
                        style = MaterialTheme.typography.titleMedium
                    )

                    if (state.points.isEmpty()) {
                        Text(
                            text = state.emptyMessage,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            if (state.points.size > 1) {
                FloatingActionButton(
                    onClick = onRouteReplayClick,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(vertical = 30.dp, horizontal = 64.dp),
                    containerColor = if (replayState.isRunning) {
                        MaterialTheme.colorScheme.surfaceVariant
                    } else {
                        MaterialTheme.colorScheme.primaryContainer
                    },
                    contentColor = if (replayState.isRunning) {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    } else {
                        MaterialTheme.colorScheme.onPrimaryContainer
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.PlayArrow,
                        contentDescription = stringResource(R.string.play_route_animation_content_description)
                    )
                }
            }

            RouteReplayDecorativeOverlay(
                visible = replayState.isRunning && state.points.size > 1,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}

@Composable
private fun RouteReplayDecorativeOverlay(
    visible: Boolean,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(durationMillis = ReplayOverlayFadeDurationMillis)),
        exit = fadeOut(animationSpec = tween(durationMillis = ReplayOverlayFadeDurationMillis)),
        modifier = modifier
    ) {
        val panelColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.26f)
                .background(
                    brush = Brush.verticalGradient(
                        colorStops = arrayOf(
                            0f to panelColor.copy(alpha = 0f),
                            ReplayOverlayGradientFadeEnd to panelColor,
                            1f to panelColor
                        )
                    )
                ),
            contentAlignment = Alignment.BottomStart
        ) {
            AnimatedPlane(modifier = Modifier.padding(horizontal = 24.dp))
        }
    }
}

@Composable
private fun AnimatedPlane(
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "plane_floating_transition")

    val offsetX by infiniteTransition.animateValue(
        initialValue = (-8).dp,
        targetValue = 8.dp,
        typeConverter = Dp.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = PlaneOffsetXDurationMillis,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "plane_offset_x"
    )

    val offsetY by infiniteTransition.animateValue(
        initialValue = (-4).dp,
        targetValue = 4.dp,
        typeConverter = Dp.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = PlaneOffsetYDurationMillis,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "plane_offset_y"
    )

    Image(
        painter = painterResource(id = R.drawable.plane),
        contentDescription = null,
        modifier = modifier
            .size(256.dp)
            .offset(x = offsetX, y = offsetY)
    )
}

@Preview(showBackground = true)
@Composable
private fun RouteReplayDecorativeOverlayPreview() {
    ParcelOnMapTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(360.dp)
                .background(Color.DarkGray),
            contentAlignment = Alignment.BottomCenter,
        ) {
            RouteReplayDecorativeOverlay(
                visible = true,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}

private const val ReplayOverlayFadeDurationMillis = 1000
private const val ReplayOverlayGradientFadeEnd = 0.15f
private const val PlaneOffsetXDurationMillis = 2600
private const val PlaneOffsetYDurationMillis = 3400
