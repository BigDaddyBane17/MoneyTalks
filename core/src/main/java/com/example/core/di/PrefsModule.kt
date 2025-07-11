package com.example.core.di

import com.example.core.domain.repository.SelectedAccountRepository
import com.example.core.prefs.UserPreferences
import com.example.core.prefs.UserPreferencesImpl
import com.example.core.repository.SelectedAccountRepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class PrefsModule {

    @Binds
    @Singleton
    abstract fun bindUserPreferences(impl: UserPreferencesImpl): UserPreferences

    @Binds
    @Singleton
    abstract fun bindSelectedAccountRepository(impl: SelectedAccountRepositoryImpl): SelectedAccountRepository
} 