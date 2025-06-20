package com.example.moneytalks.domain.repository

import com.example.moneytalks.data.remote.model.AccountDto
import com.example.moneytalks.data.remote.model.AccountCreateRequestDto
import com.example.moneytalks.data.remote.model.AccountHistoryResponseDto
import com.example.moneytalks.data.remote.model.AccountResponseDto
import com.example.moneytalks.data.remote.model.AccountUpdateRequestDto
import com.example.moneytalks.data.remote.model.CategoryDto
import com.example.moneytalks.data.remote.model.TransactionDto
import com.example.moneytalks.data.remote.model.TransactionRequestDto
import com.example.moneytalks.data.remote.model.TransactionResponseDto

interface BaseRepository {

    suspend fun getAccounts(): List<AccountDto>
    suspend fun getAccountById(id: Int): AccountResponseDto
    suspend fun createAccount(request: AccountCreateRequestDto): AccountDto
    suspend fun updateAccount(id: Int, request: AccountUpdateRequestDto): AccountDto
    suspend fun deleteAccount(id: Int)
    suspend fun getAccountHistory(id: Int): AccountHistoryResponseDto

    suspend fun getCategories(): List<CategoryDto>
    suspend fun getCategoriesByType(isIncome: Boolean): List<CategoryDto>

    suspend fun createTransaction(request: TransactionRequestDto): TransactionDto
    suspend fun getTransactionById(id: Int): TransactionResponseDto
    suspend fun updateTransaction(id: Int, request: TransactionRequestDto): TransactionResponseDto
    suspend fun deleteTransaction(id: Int)
    suspend fun getTransactionsByPeriod(accountId: Int, startDate: String?, endDate: String?): List<TransactionResponseDto>


}