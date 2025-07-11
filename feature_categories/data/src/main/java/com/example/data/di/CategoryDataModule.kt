package com.example.data.di

import com.example.core.domain.repository.CategoryRepository
import com.example.data.api.CategoryApiService
import com.example.data.repository.CategoryRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
abstract class CategoryDataModule {

    @Binds
    abstract fun bindCategoryRepository(
        impl: CategoryRepositoryImpl
    ): CategoryRepository

    companion object {
        @Provides
        fun provideCategoryApiService(retrofit: Retrofit): CategoryApiService {
            return retrofit.create(CategoryApiService::class.java)
        }
    }
} 