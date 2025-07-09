package com.example.moneytalks.features.account.data.datasource

import com.example.moneytalks.features.account.data.model.AccountCreateRequestDto
import com.example.moneytalks.features.account.data.model.AccountDto
import com.example.moneytalks.features.account.data.model.AccountHistoryResponseDto
import com.example.moneytalks.features.account.data.model.AccountResponseDto
import com.example.moneytalks.features.account.data.model.AccountUpdateRequestDto

interface AccountRemoteDataSource {
    suspend fun getAccounts(): List<AccountDto>
    suspend fun getAccountById(id: Int): AccountResponseDto
    suspend fun createAccount(request: AccountCreateRequestDto): AccountDto
    suspend fun updateAccount(id: Int, request: AccountUpdateRequestDto): AccountDto
    suspend fun deleteAccount(id: Int)
    suspend fun getAccountHistory(id: Int): AccountHistoryResponseDto
}