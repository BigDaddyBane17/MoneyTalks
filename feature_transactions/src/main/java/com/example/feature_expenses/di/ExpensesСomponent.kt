package com.example.feature_expenses.di

import androidx.lifecycle.ViewModelProvider
import com.example.core.di.FeatureComponent
import com.example.core.domain.repository.AccountRepository
import com.example.core.domain.repository.CategoryRepository
import com.example.data.di.TransactionDataModule
import com.example.domain.usecase.GetTransactionByIdUseCase
import com.example.domain.usecase.UpdateTransactionUseCase
import com.example.domain.usecase.DeleteTransactionUseCase
import dagger.Component

@ExpensesScope
@Component(
    modules = [
        TransactionDataModule::class,
        TransactionUseCaseModule::class,
        ExpensesViewModelModule::class,
        GetCurrentAccountUseCaseModule::class
    ],
    dependencies = [FeatureComponent::class]
)
interface ExpensesComponent {
    
    fun viewModelFactory(): ViewModelProvider.Factory
    
    fun getTransactionByIdUseCase(): GetTransactionByIdUseCase
    fun updateTransactionUseCase(): UpdateTransactionUseCase
    fun deleteTransactionUseCase(): DeleteTransactionUseCase
    fun accountRepository(): AccountRepository
    fun categoryRepository(): CategoryRepository
    
    @Component.Factory
    interface Factory {
        fun create(
            featureComponent: FeatureComponent
        ): ExpensesComponent
    }
} 