package com.example.feature_account.domain.usecase

import com.example.core.domain.models.Account
import com.example.core.domain.repository.AccountRepository
import javax.inject.Inject

class EditAccountUseCase @Inject constructor(
    private val repository: AccountRepository
) {
    suspend operator fun invoke(
        accountId: Int,
        name: String,
        currency: String,
        balance: String? = null
    ): Account {
        val currentAccount = repository.getAccountById(accountId)
            ?: throw IllegalArgumentException("Account not found")
        
        val updatedAccount = currentAccount.copy(
            name = name,
            currency = currency,
            balance = balance ?: currentAccount.balance
        )
        
        return repository.updateAccount(updatedAccount)
    }
} 