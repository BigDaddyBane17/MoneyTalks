package com.example.core.prefs

import com.example.core.domain.models.Account

interface UserPreferences {
    suspend fun setSelectedAccount(account: Account)
    suspend fun getSelectedAccount(): Account?
    suspend fun setSelectedAccountId(accountId: Int)
    suspend fun getSelectedAccountId(): Int?
    suspend fun clearSelectedAccount()
} 