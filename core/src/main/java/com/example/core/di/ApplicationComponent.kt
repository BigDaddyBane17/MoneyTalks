package com.example.core.di

import android.content.Context
import com.example.core.domain.repository.SelectedAccountRepository
import com.example.core.prefs.UserPreferences
import com.example.core.sync.SyncManager
import com.example.core.usecase.SyncTransactionsUseCase
import com.example.core.viewmodel.SyncViewModel
import com.example.core.workers.SyncWorkerFactory
import dagger.BindsInstance
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        PrefsModule::class,
        DatabaseModule::class,
        WorkManagerModule::class,
        SyncModule::class,
        ApiModule::class
    ]
)
interface ApplicationComponent {
    
    fun retrofit(): Retrofit
    fun context(): Context
    fun userPreferences(): UserPreferences
    fun selectedAccountRepository(): SelectedAccountRepository
    fun syncWorkerFactory(): SyncWorkerFactory
    fun syncManager(): SyncManager
    fun syncTransactionsUseCase(): SyncTransactionsUseCase
    fun syncViewModel(): SyncViewModel

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }
} 