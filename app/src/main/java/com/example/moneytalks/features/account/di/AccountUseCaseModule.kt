package com.example.moneytalks.features.account.di

import com.example.moneytalks.features.account.domain.usecase.GetAccountsUseCase
import com.example.moneytalks.features.account.domain.usecase.GetAccountsUseCaseImpl
import com.example.moneytalks.features.account.domain.usecase.UpdateAccountUseCase
import com.example.moneytalks.features.account.domain.usecase.UpdateAccountUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AccountUseCaseModule {

    @Binds
    abstract fun bindGetAccountsUseCase(impl: GetAccountsUseCaseImpl): GetAccountsUseCase

    @Binds
    abstract fun bindUpdateAccountUseCase(impl: UpdateAccountUseCaseImpl): UpdateAccountUseCase
}
