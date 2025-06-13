package com.example.moneytalks.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<SettingsUiState>(SettingsUiState.Loading)
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    fun handleIntent(intent: SettingsIntent) {
        when (intent) {
            is SettingsIntent.LoadSettings -> loadSettings()
            is SettingsIntent.ToggleDarkMode -> toggleDarkMode(intent.enabled)
            is SettingsIntent.SelectSetting -> selectSetting(intent.settingName)
        }
    }

    private fun loadSettings() {
        _uiState.value = SettingsUiState.Loading
        viewModelScope.launch {
            delay(300) // Имитация загрузки данных (например, из DataStore)
            _uiState.value = SettingsUiState.Success(
                isDarkMode = false,
                settingsList = listOf(
                    "Основной цвет",
                    "Звуки",
                    "Хаптики",
                    "Код пароль",
                    "Синхронизация",
                    "Язык",
                    "О программе"
                )
            )
        }
    }

    private fun toggleDarkMode(enabled: Boolean) {
        val currentState = _uiState.value as? SettingsUiState.Success
        currentState?.let {
            _uiState.value = it.copy(isDarkMode = enabled)
        }
    }

    private fun selectSetting(settingName: String) {
        val currentState = _uiState.value as? SettingsUiState.Success
        currentState?.let {
            _uiState.value = it.copy(selectedSetting = settingName)
        }
    }
}