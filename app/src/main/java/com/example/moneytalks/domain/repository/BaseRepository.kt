package com.example.moneytalks.domain.repository

import com.example.moneytalks.data.remote.model.Account
import com.example.moneytalks.data.remote.model.AccountCreateRequest
import com.example.moneytalks.data.remote.model.AccountHistoryResponse
import com.example.moneytalks.data.remote.model.AccountResponse
import com.example.moneytalks.data.remote.model.AccountUpdateRequest
import com.example.moneytalks.data.remote.model.Category
import com.example.moneytalks.data.remote.model.Transaction
import com.example.moneytalks.data.remote.model.TransactionRequest
import com.example.moneytalks.data.remote.model.TransactionResponse

interface BaseRepository {

    suspend fun getAccounts(): List<Account>
    suspend fun getAccountById(id: Int): AccountResponse
    suspend fun createAccount(request: AccountCreateRequest): Account
    suspend fun updateAccount(id: Int, request: AccountUpdateRequest): Account
    suspend fun deleteAccount(id: Int)
    suspend fun getAccountHistory(id: Int): AccountHistoryResponse

    suspend fun getCategories(): List<Category>
    suspend fun getCategoriesByType(isIncome: Boolean): List<Category>

    suspend fun createTransaction(request: TransactionRequest): Transaction
    suspend fun getTransactionById(id: Int): TransactionResponse
    suspend fun updateTransaction(id: Int, request: TransactionRequest): TransactionResponse
    suspend fun deleteTransaction(id: Int)
    suspend fun getTransactionsByPeriod(accountId: Int, startDate: String?, endDate: String?): List<TransactionResponse>


}