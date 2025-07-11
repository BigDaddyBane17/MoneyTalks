package com.example.feature_categories.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

@Module
abstract class CategoriesViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(
        factory: CategoriesViewModelFactory
    ): ViewModelProvider.Factory
}