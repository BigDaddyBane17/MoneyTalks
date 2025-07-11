package com.example.core.prefs

interface UserPreferences {
    suspend fun setSelectedAccountId(accountId: Int)
    suspend fun getSelectedAccountId(): Int?
    suspend fun clearSelectedAccount()
} 