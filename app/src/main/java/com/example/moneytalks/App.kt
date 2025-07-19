package com.example.moneytalks

import android.app.Application
import android.util.Log
import androidx.work.Configuration
import com.example.core.di.ApplicationComponent
import com.example.core.di.ComponentProvider
import com.example.core.di.FeatureComponent
import com.example.core.di.FeatureComponentProvider
import com.example.moneytalks.di.DaggerAppComponent


class App : Application(), ComponentProvider, FeatureComponentProvider {
    
    private val appComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }
    
    override fun provideApplicationComponent(): ApplicationComponent {
        return appComponent
    }
    
    override fun provideFeatureComponent(): FeatureComponent {
        return appComponent
    }

    override fun onCreate() {
        super.onCreate()
        
        Log.d("App", "Инициализируем WorkManager")
        
        try {
            // Инициализируем WorkManager с нашей фабрикой
            val workManagerConfig = Configuration.Builder()
                .setWorkerFactory(appComponent.syncWorkerFactory())
                .build()
            
            androidx.work.WorkManager.initialize(this, workManagerConfig)
            Log.d("App", "WorkManager инициализирован успешно")
            
            appComponent.syncManager().startPeriodicSync()
            
            appComponent.syncManager().checkSyncStatus()
            
            Log.d("App", "Приложение инициализировано")
        } catch (e: Exception) {
            Log.e("App", "Ошибка при инициализации: ${e.message}", e)
        }
    }
}
