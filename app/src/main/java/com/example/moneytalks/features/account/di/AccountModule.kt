package com.example.moneytalks.features.account.di

import com.example.moneytalks.features.account.data.datasource.AccountRemoteDataSourceImpl
import com.example.moneytalks.features.account.data.api.AccountApiService
import com.example.moneytalks.features.account.data.datasource.AccountRemoteDataSource
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
    fun provideAccountRepository(accountApiService: AccountApiService): AccountRemoteDataSource =
        AccountRemoteDataSourceImpl(accountApiService)

}
