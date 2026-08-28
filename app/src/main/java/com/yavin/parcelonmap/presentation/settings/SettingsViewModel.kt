package com.yavin.parcelonmap.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yavin.parcelonmap.data.settings.AppSettingsRepository
import com.yavin.parcelonmap.ui.settings.SettingsUiState
import com.yavin.parcelonmap.ui.theme.AppThemeMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: AppSettingsRepository,
    private val importMockParcels: ImportMockParcelsUseCase
) : ViewModel() {

    private val isSampleDataImporting = MutableStateFlow(false)
    private val _events = MutableSharedFlow<SettingsEvent>(extraBufferCapacity = 1)

    val events: SharedFlow<SettingsEvent> = _events

    val uiState: StateFlow<SettingsUiState> = combine(
        repository.themeMode,
        isSampleDataImporting
    ) { mode, importing ->
        SettingsUiState(
            themeMode = mode,
            isSampleDataImporting = importing
        )
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = SettingsUiState()
        )

    fun onThemeModeSelected(mode: AppThemeMode) {
        viewModelScope.launch {
            repository.setThemeMode(mode)
        }
    }

    fun onLoadSampleDataClick() {
        if (isSampleDataImporting.value) {
            return
        }

        viewModelScope.launch {
            isSampleDataImporting.update { true }
            try {
                runCatching {
                    importMockParcels()
                }.onSuccess {
                    _events.emit(SettingsEvent.SampleDataImportSucceeded)
                }.onFailure {
                    _events.emit(SettingsEvent.SampleDataImportFailed)
                }
            } finally {
                isSampleDataImporting.update { false }
            }
        }
    }

}

enum class SettingsEvent {
    SampleDataImportSucceeded,
    SampleDataImportFailed
}
