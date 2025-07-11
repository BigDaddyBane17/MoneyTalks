package com.example.domain.repository

import com.example.domain.models.Account

interface AccountRepository {
    suspend fun getAccounts(): List<Account>
}