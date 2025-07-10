package com.example.feature_expenses.di

import androidx.lifecycle.ViewModelProvider
import com.example.core.di.ApplicationComponent
import com.example.data.di.TransactionDataModule
import com.example.domain.di.TransactionDomainModule
import com.example.data.di.AccountDataModule
import dagger.Component

@ExpensesScope
@Component(
    modules = [
        TransactionDataModule::class,
        TransactionDomainModule::class,
        ExpensesViewModelModule::class,
        AccountDataModule::class
    ],
    dependencies = [ApplicationComponent::class]
)
interface ExpensesComponent {
    
    fun viewModelFactory(): ViewModelProvider.Factory
    
    @Component.Factory
    interface Factory {
        fun create(applicationComponent: ApplicationComponent): ExpensesComponent
    }
} 