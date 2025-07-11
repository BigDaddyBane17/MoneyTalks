package com.example.feature_expenses.di

import com.example.core.domain.repository.AccountRepository
import com.example.core.domain.repository.CategoryRepository
import com.example.domain.repository.TransactionRepository
import com.example.domain.usecase.CreateTransactionUseCase
import com.example.domain.usecase.GetTransactionByIdUseCase
import com.example.domain.usecase.UpdateTransactionUseCase
import com.example.domain.usecase.DeleteTransactionUseCase
import com.example.feature_expenses.usecase.GetAccountsUseCase
import com.example.feature_expenses.usecase.GetCategoriesUseCase
import com.example.feature_expenses.usecase.GetTodayExpensesUseCase
import com.example.feature_expenses.usecase.GetTodayIncomesUseCase
import dagger.Module
import dagger.Provides

@Module
object TransactionUseCaseModule {

    @Provides
    fun provideGetAccountsUseCase(
        accountRepository: AccountRepository
    ): GetAccountsUseCase {
        return GetAccountsUseCase(accountRepository)
    }

    @Provides
    fun provideGetCategoriesUseCase(
        categoryRepository: CategoryRepository
    ): GetCategoriesUseCase {
        return GetCategoriesUseCase(categoryRepository)
    }

    @Provides
    fun provideCreateTransactionUseCase(
        transactionRepository: TransactionRepository
    ): CreateTransactionUseCase {
        return CreateTransactionUseCase(transactionRepository)
    }

    @Provides
    fun provideGetTodayExpensesUseCase(
        transactionRepository: TransactionRepository
    ): GetTodayExpensesUseCase {
        return GetTodayExpensesUseCase(transactionRepository)
    }

    @Provides
    fun provideGetTodayIncomesUseCase(
        transactionRepository: TransactionRepository
    ): GetTodayIncomesUseCase {
        return GetTodayIncomesUseCase(transactionRepository)
    }

    @Provides
    fun provideGetTransactionByIdUseCase(
        transactionRepository: com.example.domain.repository.TransactionRepository
    ): GetTransactionByIdUseCase {
        return GetTransactionByIdUseCase(transactionRepository)
    }

    @Provides
    fun provideUpdateTransactionUseCase(
        transactionRepository: com.example.domain.repository.TransactionRepository
    ): UpdateTransactionUseCase {
        return UpdateTransactionUseCase(transactionRepository)
    }

    @Provides
    fun provideDeleteTransactionUseCase(
        transactionRepository: com.example.domain.repository.TransactionRepository
    ): DeleteTransactionUseCase {
        return DeleteTransactionUseCase(transactionRepository)
    }
} 