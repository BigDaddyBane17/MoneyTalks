package com.example.core.domain.repository

import kotlinx.coroutines.flow.StateFlow

interface SelectedAccountRepository {
    val selectedAccountIdFlow: StateFlow<Int?>
    
    suspend fun setSelectedAccountId(accountId: Int)
    suspend fun getSelectedAccountId(): Int?
    suspend fun clearSelectedAccount()
} 