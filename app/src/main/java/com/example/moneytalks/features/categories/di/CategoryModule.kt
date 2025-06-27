package com.example.moneytalks.features.categories.di

import com.example.moneytalks.features.categories.data.CategoryRepositoryImpl
import com.example.moneytalks.features.categories.data.remote.CategoryApiService
import com.example.moneytalks.features.categories.domain.repository.CategoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object CategoryModule {

    @Provides
    fun provideCategoryApiService(retrofit: Retrofit): CategoryApiService =
        retrofit.create(CategoryApiService::class.java)

    @Provides
    fun provideCategoryRepository(categoryApiService: CategoryApiService): CategoryRepository =
        CategoryRepositoryImpl(categoryApiService)
}