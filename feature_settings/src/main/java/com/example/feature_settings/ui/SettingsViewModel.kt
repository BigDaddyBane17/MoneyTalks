package com.example.feature_settings.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_ui.theme.AppTheme
import com.example.core_ui.theme.ThemeProvider
import com.example.feature_settings.domain.usecase.GetSettingsUseCase
import com.example.feature_settings.domain.usecase.UpdateSettingsUseCase
import com.example.feature_settings.models.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val getSettingsUseCase: GetSettingsUseCase,
    private val updateSettingsUseCase: UpdateSettingsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        loadSettings()
    }

    private fun loadSettings() {
        viewModelScope.launch {
            // Объединяем потоки по частям
            combine(
                getSettingsUseCase.themeMode,
                getSettingsUseCase.appTheme,
                getSettingsUseCase.hapticEnabled,
                getSettingsUseCase.hapticMode
            ) { themeMode, appTheme, hapticEnabled, hapticMode ->
                _uiState.value = _uiState.value.copy(
                    themeMode = themeMode,
                    appTheme = appTheme,
                    hapticEnabled = hapticEnabled,
                    hapticMode = hapticMode
                )
            }.collect()

            combine(
                getSettingsUseCase.pinCode,
                getSettingsUseCase.syncFrequency,
                getSettingsUseCase.syncFrequencyHours,
                getSettingsUseCase.appLanguage
            ) { pinCode, syncFrequency, syncFrequencyHours, appLanguage ->
                _uiState.value = _uiState.value.copy(
                    pinCode = pinCode,
                    syncFrequency = syncFrequency,
                    syncFrequencyHours = syncFrequencyHours,
                    appLanguage = appLanguage,
                    isLoading = false
                )
            }.collect()
        }
    }

    fun updateThemeMode(themeMode: ThemeMode) {
        viewModelScope.launch {
            updateSettingsUseCase.updateThemeMode(themeMode)
        }
    }

    fun updateAppTheme(appTheme: AppTheme) {
        viewModelScope.launch {
            updateSettingsUseCase.updateAppTheme(appTheme)
        }
    }

    fun updateHapticEnabled(enabled: Boolean) {
        viewModelScope.launch {
            updateSettingsUseCase.updateHapticEnabled(enabled)
        }
    }

    fun updateHapticMode(hapticMode: HapticMode) {
        viewModelScope.launch {
            updateSettingsUseCase.updateHapticMode(hapticMode)
        }
    }

    fun updatePinCode(pinCode: String?) {
        viewModelScope.launch {
            updateSettingsUseCase.updatePinCode(pinCode)
        }
    }

    fun updateSyncFrequency(syncFrequency: SyncFrequency) {
        viewModelScope.launch {
            updateSettingsUseCase.updateSyncFrequency(syncFrequency)
        }
    }

    fun updateSyncFrequencyHours(hours: Int) {
        viewModelScope.launch {
            // Сохраняем кастомное количество часов
            updateSettingsUseCase.updateSyncFrequencyHours(hours)
        }
    }

    fun updateAppLanguage(appLanguage: AppLanguage) {
        viewModelScope.launch {
            updateSettingsUseCase.updateAppLanguage(appLanguage)
        }
    }
}

data class SettingsUiState(
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val appTheme: AppTheme = ThemeProvider.availableThemes.first(),
    val hapticEnabled: Boolean = true,
    val hapticMode: HapticMode = HapticMode.MEDIUM,
    val pinCode: String? = null,
    val syncFrequency: SyncFrequency = SyncFrequency.DAILY,
    val syncFrequencyHours: Int = 24,
    val appLanguage: AppLanguage = AppLanguage.RUSSIAN,
    val isLoading: Boolean = true
)