package com.example.feature_expenses.di

import androidx.lifecycle.ViewModelProvider
import com.example.core.di.FeatureComponent
import com.example.data.di.TransactionDataModule
import dagger.Component

@IncomesScope
@Component(
    modules = [
        TransactionDataModule::class,
        IncomesViewModelModule::class,
        GetCurrentAccountUseCaseModule::class
    ],
    dependencies = [FeatureComponent::class]
)
interface IncomesComponent {
    
    fun viewModelFactory(): ViewModelProvider.Factory
    
    @Component.Factory
    interface Factory {
        fun create(
            featureComponent: FeatureComponent
        ): IncomesComponent
    }
} 