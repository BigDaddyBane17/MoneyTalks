package com.example.core.domain.repository

import com.example.core.domain.models.Account
import kotlinx.coroutines.flow.StateFlow

interface SelectedAccountRepository {
    val selectedAccountFlow: StateFlow<Account?>
    
    suspend fun setSelectedAccount(account: Account)
    suspend fun getSelectedAccount(): Account?
    suspend fun clearSelectedAccount()
} 