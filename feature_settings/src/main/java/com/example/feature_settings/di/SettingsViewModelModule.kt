package com.example.feature_settings.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

@Module
abstract class SettingsViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(
        factory: SettingsViewModelFactory
    ): ViewModelProvider.Factory
} 