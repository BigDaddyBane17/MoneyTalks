package com.example.moneytalks.data

import com.example.moneytalks.data.remote.ApiService
import com.example.moneytalks.data.remote.model.Account
import com.example.moneytalks.data.remote.model.AccountCreateRequest
import com.example.moneytalks.data.remote.model.AccountHistoryResponse
import com.example.moneytalks.data.remote.model.AccountResponse
import com.example.moneytalks.data.remote.model.AccountUpdateRequest
import com.example.moneytalks.data.remote.model.Category
import com.example.moneytalks.data.remote.model.Transaction
import com.example.moneytalks.data.remote.model.TransactionRequest
import com.example.moneytalks.data.remote.model.TransactionResponse
import com.example.moneytalks.domain.repository.BaseRepository

class BaseRepositoryImpl(
    private val api: ApiService
): BaseRepository {
    override suspend fun getAccounts(): List<Account> {
        return api.getAccounts()
    }

    override suspend fun getAccountById(id: Int): AccountResponse {
        return api.getAccountById(id)
    }

    override suspend fun createAccount(request: AccountCreateRequest): Account {
        return api.createAccount(request)
    }

    override suspend fun updateAccount(
        id: Int,
        request: AccountUpdateRequest
    ): Account {
        return api.updateAccountById(id, request)
    }

    override suspend fun deleteAccount(id: Int) {
        return api.deleteAccountById(id)
    }

    override suspend fun getAccountHistory(id: Int): AccountHistoryResponse {
        return api.getAccountHistory(id)
    }

    override suspend fun getCategories(): List<Category> {
        return api.getCategories()
    }

    override suspend fun getCategoriesByType(isIncome: Boolean): List<Category> {
        return api.getCategoriesByType(isIncome)
    }

    override suspend fun createTransaction(request: TransactionRequest): Transaction {
        return api.createTransaction(request)
    }

    override suspend fun getTransactionById(id: Int): TransactionResponse {
        return api.getTransactionById(id)
    }

    override suspend fun updateTransaction(
        id: Int,
        request: TransactionRequest
    ): TransactionResponse {
        return api.updateTransaction(id, request)
    }

    override suspend fun deleteTransaction(id: Int) {
        return api.deleteTransaction(id)
    }

    override suspend fun getTransactionsByPeriod(
        accountId: Int,
        startDate: String?,
        endDate: String?
    ): List<TransactionResponse> {
        return api.getTransactionsByPeriod(accountId, startDate, endDate)
    }
}