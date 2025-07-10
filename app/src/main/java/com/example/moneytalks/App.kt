package com.example.moneytalks

import android.app.Application
import com.example.core.di.ApplicationComponent
import com.example.core.di.ComponentProvider
import com.example.core.di.DaggerApplicationComponent

class App : Application(), ComponentProvider {
    
    val applicationComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
    
    override fun provideApplicationComponent(): ApplicationComponent {
        return applicationComponent
    }
}
