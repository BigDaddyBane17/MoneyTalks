package com.example.data.di

import com.example.core.sync.SyncRepository
import com.example.data.api.TransactionApiService
import com.example.data.repository.TransactionRepositoryImpl
import com.example.domain.repository.TransactionRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
abstract class TransactionDataModule {

    @Binds
    abstract fun bindTransactionRepository(
        impl: TransactionRepositoryImpl
    ): TransactionRepository

    companion object {
        @Provides
        fun provideTransactionApiService(retrofit: Retrofit): TransactionApiService {
            return retrofit.create(TransactionApiService::class.java)
        }
    }
} 