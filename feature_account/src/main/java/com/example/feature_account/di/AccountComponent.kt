package com.example.feature_account.di

import androidx.lifecycle.ViewModelProvider
import com.example.core.di.FeatureComponent
import dagger.Component

@AccountScope
@Component(
    modules = [
        AccountViewModelModule::class,
        GetCurrentAccountUseCaseModule::class
    ],
    dependencies = [FeatureComponent::class]
)
interface AccountComponent {
    
    fun viewModelFactory(): ViewModelProvider.Factory
    
    @Component.Factory
    interface Factory {
        fun create(
            featureComponent: FeatureComponent
        ): AccountComponent
    }
}