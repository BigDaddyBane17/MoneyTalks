package com.example.feature_expenses.di

import androidx.lifecycle.ViewModelProvider
import com.example.core.di.FeatureComponent
import com.example.data.di.TransactionDataModule
import dagger.Component

@ExpensesScope
@Component(
    modules = [
        TransactionDataModule::class,
        ExpensesViewModelModule::class,
        GetCurrentAccountUseCaseModule::class
    ],
    dependencies = [FeatureComponent::class]
)
interface ExpensesComponent {
    
    fun viewModelFactory(): ViewModelProvider.Factory
    
    @Component.Factory
    interface Factory {
        fun create(
            featureComponent: FeatureComponent
        ): ExpensesComponent
    }
} 