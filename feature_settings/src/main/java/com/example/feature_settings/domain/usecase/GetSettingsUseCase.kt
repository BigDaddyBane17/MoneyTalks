package com.example.feature_settings.domain.usecase

import com.example.core.prefs.SettingsPreferences
import com.example.core_ui.theme.AppTheme
import com.example.core_ui.theme.ThemeProvider
import com.example.feature_settings.models.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetSettingsUseCase @Inject constructor(
    private val settingsPreferences: SettingsPreferences
) {
    val themeMode: Flow<ThemeMode> = settingsPreferences.themeMode.map { mode ->
        ThemeMode.valueOf(mode)
    }

    val appTheme: Flow<AppTheme> = settingsPreferences.appThemeId.map { themeId ->
        ThemeProvider.getThemeById(themeId)
    }

    val hapticEnabled: Flow<Boolean> = settingsPreferences.hapticEnabled

    val hapticMode: Flow<HapticMode> = settingsPreferences.hapticMode.map { mode ->
        HapticMode.valueOf(mode)
    }

    val pinCode: Flow<String?> = settingsPreferences.pinCode

    val syncFrequency: Flow<SyncFrequency> = settingsPreferences.syncFrequency.map { frequency ->
        SyncFrequency.valueOf(frequency)
    }

    val syncFrequencyHours: Flow<Int> = settingsPreferences.syncFrequencyHours

    val appLanguage: Flow<AppLanguage> = settingsPreferences.appLanguage.map { language ->
        AppLanguage.values().find { it.code == language } ?: AppLanguage.RUSSIAN
    }
} 