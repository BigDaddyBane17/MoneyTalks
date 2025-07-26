package com.example.core.prefs

import kotlinx.coroutines.flow.Flow

interface SettingsPreferences {
    val themeMode: Flow<String>
    val appThemeId: Flow<String>
    val hapticEnabled: Flow<Boolean>
    val hapticMode: Flow<String>
    val pinCode: Flow<String?>
    val syncFrequency: Flow<String>
    val syncFrequencyHours: Flow<Int>
    val appLanguage: Flow<String>
    
    suspend fun setThemeMode(mode: String)
    suspend fun setAppTheme(themeId: String)
    suspend fun setHapticEnabled(enabled: Boolean)
    suspend fun setHapticMode(mode: String)
    suspend fun setPinCode(pinCode: String?)
    suspend fun setSyncFrequency(frequency: String)
    suspend fun setSyncFrequencyHours(hours: Int)
    suspend fun setAppLanguage(language: String)
} 