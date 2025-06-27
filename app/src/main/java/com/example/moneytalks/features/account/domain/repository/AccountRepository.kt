package com.example.moneytalks.features.account.domain.repository

import com.example.moneytalks.features.account.data.remote.model.AccountCreateRequestDto
import com.example.moneytalks.features.account.data.remote.model.AccountDto
import com.example.moneytalks.features.account.data.remote.model.AccountHistoryResponseDto
import com.example.moneytalks.features.account.data.remote.model.AccountResponseDto
import com.example.moneytalks.features.account.data.remote.model.AccountUpdateRequestDto

interface AccountRepository {
    suspend fun getAccounts(): List<AccountDto>
    suspend fun getAccountById(id: Int): AccountResponseDto
    suspend fun createAccount(request: AccountCreateRequestDto): AccountDto
    suspend fun updateAccount(id: Int, request: AccountUpdateRequestDto): AccountDto
    suspend fun deleteAccount(id: Int)
    suspend fun getAccountHistory(id: Int): AccountHistoryResponseDto
}
