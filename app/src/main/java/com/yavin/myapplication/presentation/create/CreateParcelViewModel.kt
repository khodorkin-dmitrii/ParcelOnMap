package com.yavin.myapplication.presentation.create

import androidx.lifecycle.ViewModel
import com.yavin.myapplication.ui.parcel.create.CreateParcelUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class CreateParcelViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(CreateParcelUiState())
    val uiState: StateFlow<CreateParcelUiState> = _uiState.asStateFlow()

    fun onTrackingNumberChanged(value: String) {
        _uiState.update { state ->
            state.copy(trackingNumber = value)
        }
    }

    fun onSaveClick() {
        _uiState.update { state ->
            state.copy(showNotImplementedMessage = true)
        }
    }
}
