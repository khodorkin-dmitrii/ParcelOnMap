package com.yavin.parcelonmap.ui.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yavin.parcelonmap.ui.R
import com.yavin.parcelonmap.ui.theme.AppThemeMode
import com.yavin.parcelonmap.ui.theme.ParcelOnMapTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    themeMode: AppThemeMode,
    isSampleDataImporting: Boolean,
    snackbarHostState: SnackbarHostState,
    onThemeModeClick: (AppThemeMode) -> Unit,
    onLoadSampleDataClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.settings_title))
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = stringResource(R.string.back_content_description)
                        )
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
        ) {
            ThemeSettings(
                themeMode = themeMode,
                onThemeModeClick = onThemeModeClick
            )
            SampleDataSettings(
                isImporting = isSampleDataImporting,
                onLoadSampleDataClick = onLoadSampleDataClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            AppInfo(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp))
        }
    }
}

@Composable
private fun SampleDataSettings(
    isImporting: Boolean,
    onLoadSampleDataClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(R.string.sample_data_title),
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = stringResource(R.string.sample_data_description),
            style = MaterialTheme.typography.bodyMedium
        )
        Button(
            onClick = onLoadSampleDataClick,
            enabled = !isImporting
        ) {
            if (isImporting) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(18.dp),
                    strokeWidth = 2.dp
                )
            }
            Text(text = stringResource(R.string.load_sample_data_button))
        }
    }
}

@Composable
private fun ThemeSettings (
    themeMode: AppThemeMode,
    onThemeModeClick: (AppThemeMode) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.theme_title),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            ThemeModeOption(
                text = stringResource(R.string.theme_system_default),
                selected = themeMode == AppThemeMode.System,
                onClick = { onThemeModeClick(AppThemeMode.System) },
                modifier = Modifier.weight(1f)
            )
            ThemeModeOption(
                text = stringResource(R.string.theme_light),
                selected = themeMode == AppThemeMode.Light,
                onClick = { onThemeModeClick(AppThemeMode.Light) },
                modifier = Modifier.weight(1f)
            )
            ThemeModeOption(
                text = stringResource(R.string.theme_dark),
                selected = themeMode == AppThemeMode.Dark,
                onClick = { onThemeModeClick(AppThemeMode.Dark) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun AppInfo (
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SettingsRow(
            label = stringResource(R.string.map_provider_label),
            value = stringResource(R.string.google_maps_value)
        )
        SettingsRow(
            label = stringResource(R.string.auto_sync_parcels_label),
            value = stringResource(R.string.coming_soon_value)
        )
        SettingsRow(
            label = stringResource(R.string.notifications_label),
            value = stringResource(R.string.coming_soon_value)
        )
        SettingsRow(
            label = stringResource(R.string.app_version_label),
            value = stringResource(R.string.app_version_value)
        )
    }
}

@Composable
private fun ThemeModeOption(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick
        )
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
private fun SettingsRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingsScreenPreview() {
    ParcelOnMapTheme {
        SettingsScreen(
            themeMode = SettingsPreviewData.system.themeMode,
            isSampleDataImporting = SettingsPreviewData.system.isSampleDataImporting,
            snackbarHostState = SnackbarHostState(),
            onThemeModeClick = {},
            onLoadSampleDataClick = {},
            onBackClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingsScreenLightPreview() {
    ParcelOnMapTheme {
        SettingsScreen(
            themeMode = SettingsPreviewData.light.themeMode,
            isSampleDataImporting = SettingsPreviewData.light.isSampleDataImporting,
            snackbarHostState = SnackbarHostState(),
            onThemeModeClick = {},
            onLoadSampleDataClick = {},
            onBackClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingsScreenDarkPreview() {
    ParcelOnMapTheme {
        SettingsScreen(
            themeMode = SettingsPreviewData.dark.themeMode,
            isSampleDataImporting = SettingsPreviewData.dark.isSampleDataImporting,
            snackbarHostState = SnackbarHostState(),
            onThemeModeClick = {},
            onLoadSampleDataClick = {},
            onBackClick = {}
        )
    }
}
