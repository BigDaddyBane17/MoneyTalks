package com.example.feature_settings.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import com.example.core.di.FeatureComponentProvider

@Composable
fun getSettingsViewModelFactory(): ViewModelProvider.Factory {
    return (LocalContext.current.applicationContext as FeatureComponentProvider)
        .provideFeatureComponent()
        .let { featureComponent ->
            com.example.feature_settings.di.DaggerSettingsComponent.factory()
                .create(featureComponent)
                .viewModelFactory()
        }
} 