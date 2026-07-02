package com.yavin.myapplication.ui.parcel.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yavin.myapplication.ui.R
import com.yavin.myapplication.ui.theme.ParcelOnMapTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateParcelScreen(
    state: CreateParcelUiState,
    onTrackingNumberChange: (String) -> Unit,
    onSaveClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.add_parcel_title))
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
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = stringResource(R.string.screen_under_development_note))

            OutlinedTextField(
                value = state.trackingNumber,
                onValueChange = onTrackingNumberChange,
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(text = stringResource(R.string.tracking_number_label))
                },
                singleLine = true
            )

            Button(
                onClick = onSaveClick
            ) {
                Text(text = stringResource(R.string.save_parcel_button))
            }

            if (state.showNotImplementedMessage) {
                Text(text = stringResource(R.string.feature_not_implemented_message))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CreateParcelScreenEmptyPreview() {
    ParcelOnMapTheme {
        CreateParcelScreen(
            state = CreateParcelPreviewData.empty,
            onTrackingNumberChange = {},
            onSaveClick = {},
            onBackClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CreateParcelScreenFilledPreview() {
    ParcelOnMapTheme {
        CreateParcelScreen(
            state = CreateParcelPreviewData.filled,
            onTrackingNumberChange = {},
            onSaveClick = {},
            onBackClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CreateParcelScreenWithMessagePreview() {
    ParcelOnMapTheme {
        CreateParcelScreen(
            state = CreateParcelPreviewData.withMessage,
            onTrackingNumberChange = {},
            onSaveClick = {},
            onBackClick = {}
        )
    }
}
