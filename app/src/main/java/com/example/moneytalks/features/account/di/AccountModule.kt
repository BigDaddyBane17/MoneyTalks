package com.example.moneytalks.features.account.di

import com.example.moneytalks.features.account.data.AccountRepositoryImpl
import com.example.moneytalks.features.account.data.remote.AccountApiService
import com.example.moneytalks.features.account.domain.repository.AccountRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object AccountModule {

    @Provides
    fun provideAccountApiService(retrofit: Retrofit): AccountApiService =
        retrofit.create(AccountApiService::class.java)

    @Provides
    fun provideAccountRepository(accountApiService: AccountApiService): AccountRepository =
        AccountRepositoryImpl(accountApiService)
}
