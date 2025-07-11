package com.example.feature_expenses.usecase

import com.example.core.domain.models.Account
import com.example.core.domain.repository.AccountRepository
import javax.inject.Inject

class GetAccountsUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(): List<Account> {
        return accountRepository.getAccounts()
    }
} 