package com.example.feature_account.di

import com.example.core.domain.repository.AccountRepository
import com.example.core.domain.repository.SelectedAccountRepository
import com.example.core.usecase.GetCurrentAccountUseCase
import dagger.Module
import dagger.Provides

@Module
object GetCurrentAccountUseCaseModule {

    @Provides
    fun provideGetCurrentAccountUseCase(
        accountRepository: AccountRepository,
        selectedAccountRepository: SelectedAccountRepository
    ): GetCurrentAccountUseCase {
        return GetCurrentAccountUseCase(accountRepository, selectedAccountRepository)
    }
} 