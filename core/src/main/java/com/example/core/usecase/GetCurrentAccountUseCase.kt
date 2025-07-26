package com.example.core.usecase

import com.example.core.domain.models.Account
import com.example.core.domain.repository.AccountRepository
import com.example.core.domain.repository.SelectedAccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCurrentAccountUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
    private val selectedAccountRepository: SelectedAccountRepository
) {
    
    operator fun invoke(): Flow<Account?> {
        return selectedAccountRepository.selectedAccountFlow
    }
    
    suspend fun getCurrentAccount(): Account? {
        return selectedAccountRepository.getSelectedAccount()
    }
} 