package com.example.core.domain.repository

import com.example.core.domain.models.Account

interface AccountRepository {
    suspend fun getAccounts(): List<Account>
    suspend fun getAccountById(id: Int): Account?
    suspend fun updateAccount(account: Account): Account
} 