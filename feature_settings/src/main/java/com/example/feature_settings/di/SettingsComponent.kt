package com.example.feature_settings.di

import androidx.lifecycle.ViewModelProvider
import com.example.core.di.FeatureComponent
import dagger.Component

@SettingsScope
@Component(
    modules = [SettingsViewModelModule::class],
    dependencies = [FeatureComponent::class]
)
interface SettingsComponent {
    
    fun viewModelFactory(): ViewModelProvider.Factory
    
    @Component.Factory
    interface Factory {
        fun create(
            featureComponent: FeatureComponent
        ): SettingsComponent
    }
} 