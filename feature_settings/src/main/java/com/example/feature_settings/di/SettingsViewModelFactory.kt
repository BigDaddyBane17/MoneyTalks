package com.example.feature_settings.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.feature_settings.domain.usecase.GetSettingsUseCase
import com.example.feature_settings.domain.usecase.UpdateSettingsUseCase
import com.example.feature_settings.ui.SettingsViewModel
import javax.inject.Inject
import javax.inject.Provider

class SettingsViewModelFactory @Inject constructor(
    private val settingsViewModelProvider: Provider<SettingsViewModel>
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            SettingsViewModel::class.java -> settingsViewModelProvider.get() as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
        }
    }
} 