package com.example.domain.usecase

import com.example.domain.models.Account
import com.example.domain.repository.AccountRepository
import javax.inject.Inject

class GetFirstAccountUseCase @Inject constructor(
    private val repository: AccountRepository
) {
    suspend operator fun invoke(): Account? {
        return repository.getAccounts().firstOrNull()
    }
} 