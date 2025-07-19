package com.example.core.di

import com.example.core.network.TransactionApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
object ApiModule {

    @Provides
    @Singleton
    fun provideTransactionApiService(retrofit: Retrofit): TransactionApiService {
        return retrofit.create(TransactionApiService::class.java)
    }
} 