package com.example.moneytalks

import android.app.Application
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
    }
}
