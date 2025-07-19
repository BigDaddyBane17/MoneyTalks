package com.example.moneytalks.di

import android.content.Context
import com.example.core.di.ApiModule
import com.example.core.di.DatabaseModule
import com.example.core.di.FeatureComponent
import com.example.core.di.NetworkModule
import com.example.core.di.PrefsModule
import com.example.core.di.SyncModule
import com.example.core.di.WorkManagerModule
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
        AppModule::class,
        NetworkModule::class,
        PrefsModule::class,
        DatabaseModule::class,
        WorkManagerModule::class,
        SyncModule::class,
        ApiModule::class
    ]
)
interface AppComponent : FeatureComponent {
    
    override fun retrofit(): Retrofit
    override fun context(): Context
    override fun syncWorkerFactory(): SyncWorkerFactory
    override fun syncManager(): SyncManager
    override fun syncTransactionsUseCase(): SyncTransactionsUseCase
    override fun syncViewModel(): SyncViewModel
    
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
} 