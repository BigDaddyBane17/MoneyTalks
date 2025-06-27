package com.example.moneytalks.features.settings.presentation.settings


sealed class SettingsUiState {
    object Loading : SettingsUiState()
    data class Success(
        val isDarkMode: Boolean,
        val settingsList: List<String>,
        val selectedSetting: String? = null
    ) : SettingsUiState()
    data class Error(val message: String) : SettingsUiState()
}
