package com.yavin.myapplication.ui.parcel.map

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.yavin.myapplication.ui.R
import com.yavin.myapplication.ui.model.ParcelMapUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParcelMapScreen(
    state: ParcelMapUiState,
    onBackClick: () -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val topAppBarColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)
    val topAppBarContentColor = MaterialTheme.colorScheme.onSurface
    var playAnimationRequestId by remember(state.points) { mutableIntStateOf(0) }
    var isRouteAnimationRunning by remember(state.points) { mutableStateOf(false) }

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
                    animationRequestId = playAnimationRequestId,
                    onAnimationRunningChange = { isRouteAnimationRunning = it },
                    modifier = Modifier.fillMaxSize()
                )
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
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
                    onClick = {
                        if (!isRouteAnimationRunning) {
                            isRouteAnimationRunning = true
                            playAnimationRequestId += 1
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(24.dp),
                    containerColor = if (isRouteAnimationRunning) {
                        MaterialTheme.colorScheme.surfaceVariant
                    } else {
                        MaterialTheme.colorScheme.primaryContainer
                    },
                    contentColor = if (isRouteAnimationRunning) {
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
                visible = isRouteAnimationRunning && state.points.size > 1,
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
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.33f),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
                contentColor = MaterialTheme.colorScheme.onSurface
            )
        ) {
            Box(modifier = Modifier.fillMaxSize())
        }
    }
}

private const val ReplayOverlayFadeDurationMillis = 1000
