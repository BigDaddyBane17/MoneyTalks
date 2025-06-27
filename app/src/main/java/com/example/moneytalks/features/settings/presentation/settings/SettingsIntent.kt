package com.example.moneytalks.features.settings.presentation.settings

sealed class SettingsIntent {
    object LoadSettings : SettingsIntent()
    data class ToggleDarkMode(val enabled: Boolean) : SettingsIntent()
    data class SelectSetting(val settingName: String) : SettingsIntent()
}
