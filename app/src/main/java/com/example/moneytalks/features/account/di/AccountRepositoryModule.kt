package com.example.moneytalks.features.account.di

import com.example.moneytalks.features.account.domain.repository.AccountRepository
import com.example.moneytalks.features.account.data.repository.AccountRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AccountRepositoryModule {

    @Binds
    abstract fun bindAccountRepository(
        impl: AccountRepositoryImpl
    ): AccountRepository
}
