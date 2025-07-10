package com.example.data.di

import com.example.data.api.AccountApiService
import com.example.data.repository.AccountRepositoryImpl
import com.example.domain.repository.AccountRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
abstract class AccountDataModule {

    @Binds
    abstract fun bindAccountRepository(
        impl: AccountRepositoryImpl
    ): AccountRepository

    companion object {
        @Provides
        fun provideAccountApiService(retrofit: Retrofit): AccountApiService {
            return retrofit.create(AccountApiService::class.java)
        }
    }
} 