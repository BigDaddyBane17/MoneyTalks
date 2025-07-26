package com.example.feature_settings.di

import dagger.Module

@Module(
    includes = [
        SettingsViewModelModule::class
    ]
)
object SettingsModule {
    // Use cases будут создаваться автоматически через @Inject конструкторы
} 