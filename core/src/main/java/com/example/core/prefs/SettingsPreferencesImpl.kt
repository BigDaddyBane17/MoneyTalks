package com.example.core.prefs

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Singleton
class SettingsPreferencesImpl @Inject constructor(
    private val context: Context
) : SettingsPreferences {

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val encryptedPrefs = EncryptedSharedPreferences.create(
        context,
        "secure_settings",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    companion object {
        private val THEME_MODE = stringPreferencesKey("theme_mode")
        private val APP_THEME_ID = stringPreferencesKey("app_theme_id")
        private val HAPTIC_ENABLED = booleanPreferencesKey("haptic_enabled")
        private val HAPTIC_MODE = stringPreferencesKey("haptic_mode")
        private val SYNC_FREQUENCY = stringPreferencesKey("sync_frequency")
        private val SYNC_FREQUENCY_HOURS = intPreferencesKey("sync_frequency_hours")
        private val APP_LANGUAGE = stringPreferencesKey("app_language")
    }

    override val themeMode: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[THEME_MODE] ?: "SYSTEM"
    }

    override val appThemeId: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[APP_THEME_ID] ?: "default"
    }

    override val hapticEnabled: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[HAPTIC_ENABLED] ?: true
    }

    override val hapticMode: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[HAPTIC_MODE] ?: "MEDIUM"
    }

    override val pinCode: Flow<String?> = kotlinx.coroutines.flow.flow {
        emit(encryptedPrefs.getString("pin_code", null))
    }

    override val syncFrequency: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[SYNC_FREQUENCY] ?: "DAILY"
    }

    override val syncFrequencyHours: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[SYNC_FREQUENCY_HOURS] ?: 24
    }

    override val appLanguage: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[APP_LANGUAGE] ?: "ru"
    }

    override suspend fun setThemeMode(mode: String) {
        context.dataStore.edit { preferences ->
            preferences[THEME_MODE] = mode
        }
    }

    override suspend fun setAppTheme(themeId: String) {
        context.dataStore.edit { preferences ->
            preferences[APP_THEME_ID] = themeId
        }
    }

    override suspend fun setHapticEnabled(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[HAPTIC_ENABLED] = enabled
        }
    }

    override suspend fun setHapticMode(mode: String) {
        context.dataStore.edit { preferences ->
            preferences[HAPTIC_MODE] = mode
        }
    }

    override suspend fun setPinCode(pinCode: String?) {
        encryptedPrefs.edit().putString("pin_code", pinCode).apply()
    }

    override suspend fun setSyncFrequency(frequency: String) {
        context.dataStore.edit { preferences ->
            preferences[SYNC_FREQUENCY] = frequency
        }
    }

    override suspend fun setSyncFrequencyHours(hours: Int) {
        context.dataStore.edit { preferences ->
            preferences[SYNC_FREQUENCY_HOURS] = hours
        }
    }

    override suspend fun setAppLanguage(language: String) {
        context.dataStore.edit { preferences ->
            preferences[APP_LANGUAGE] = language
        }
    }
} 