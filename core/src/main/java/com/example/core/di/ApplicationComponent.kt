package com.example.core.di

import android.content.Context
import com.example.core.domain.repository.SelectedAccountRepository
import com.example.core.prefs.UserPreferences
import dagger.BindsInstance
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        PrefsModule::class
    ]
)
interface ApplicationComponent {
    
    fun retrofit(): Retrofit
    fun context(): Context
    fun userPreferences(): UserPreferences
    fun selectedAccountRepository(): SelectedAccountRepository
    
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }
} 