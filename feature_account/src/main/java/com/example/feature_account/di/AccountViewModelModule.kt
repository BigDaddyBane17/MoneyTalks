package com.example.feature_account.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

@Module
abstract class AccountViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(
        factory: AccountViewModelFactory
    ): ViewModelProvider.Factory
}