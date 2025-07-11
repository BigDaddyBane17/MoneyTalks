package com.example.feature_expenses.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

@Module
abstract class ExpensesViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(
        factory: ExpensesViewModelFactory
    ): ViewModelProvider.Factory
} 