package com.example.feature_settings.domain.usecase

import com.example.core.prefs.SettingsPreferences
import com.example.core.sync.SyncManager
import com.example.core_ui.theme.AppTheme
import com.example.feature_settings.models.*
import javax.inject.Inject

class UpdateSettingsUseCase @Inject constructor(
    private val settingsPreferences: SettingsPreferences,
    private val syncManager: SyncManager
) {
    suspend fun updateThemeMode(themeMode: ThemeMode) {
        settingsPreferences.setThemeMode(themeMode.name)
    }

    suspend fun updateAppTheme(appTheme: AppTheme) {
        settingsPreferences.setAppTheme(appTheme.id)
    }

    suspend fun updateHapticEnabled(enabled: Boolean) {
        settingsPreferences.setHapticEnabled(enabled)
    }

    suspend fun updateHapticMode(hapticMode: HapticMode) {
        settingsPreferences.setHapticMode(hapticMode.name)
    }

    suspend fun updatePinCode(pinCode: String?) {
        settingsPreferences.setPinCode(pinCode)
    }

    suspend fun updateSyncFrequency(syncFrequency: SyncFrequency) {
        settingsPreferences.setSyncFrequency(syncFrequency.name)
        // Обновляем WorkManager с новой частотой
        if (syncFrequency.hours > 0) {
            syncManager.updatePeriodicSync(syncFrequency.hours)
        } else {
            syncManager.cancelPeriodicSync()
        }
    }

    suspend fun updateSyncFrequencyHours(hours: Int) {
        // Сохраняем кастомное количество часов
        settingsPreferences.setSyncFrequencyHours(hours)
        // Обновляем WorkManager с новой частотой
        if (hours > 0) {
            syncManager.updatePeriodicSync(hours)
        } else {
            syncManager.cancelPeriodicSync()
        }
    }

    suspend fun updateAppLanguage(appLanguage: AppLanguage) {
        settingsPreferences.setAppLanguage(appLanguage.code)
    }
} 