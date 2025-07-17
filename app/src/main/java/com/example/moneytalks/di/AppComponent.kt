package com.example.moneytalks.di

import android.content.Context
import com.example.core.di.FeatureComponent
import com.example.core.di.NetworkModule
import com.example.core.di.PrefsModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        PrefsModule::class,
        AppModule::class
    ]
)
interface AppComponent : FeatureComponent {
    
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
} 