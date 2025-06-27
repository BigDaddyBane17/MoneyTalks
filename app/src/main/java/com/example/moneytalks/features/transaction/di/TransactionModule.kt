package com.example.moneytalks.features.transaction.di

import com.example.moneytalks.features.transaction.data.TransactionRepositoryImpl
import com.example.moneytalks.features.transaction.data.remote.TransactionApiService
import com.example.moneytalks.features.transaction.domain.repository.TransactionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit


@Module
@InstallIn(SingletonComponent::class)
object TransactionModule {

    @Provides
    fun provideTransactionApiService(retrofit: Retrofit): TransactionApiService =
        retrofit.create(TransactionApiService::class.java)

    @Provides
    fun provideTransactionRepository(transactionApiService: TransactionApiService): TransactionRepository =
        TransactionRepositoryImpl(transactionApiService)
}
