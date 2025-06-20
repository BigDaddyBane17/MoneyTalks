package com.example.moneytalks.data

import com.example.moneytalks.data.remote.ApiService
import com.example.moneytalks.data.remote.model.AccountDto
import com.example.moneytalks.data.remote.model.AccountCreateRequestDto
import com.example.moneytalks.data.remote.model.AccountHistoryResponseDto
import com.example.moneytalks.data.remote.model.AccountResponseDto
import com.example.moneytalks.data.remote.model.AccountUpdateRequestDto
import com.example.moneytalks.data.remote.model.CategoryDto
import com.example.moneytalks.data.remote.model.TransactionDto
import com.example.moneytalks.data.remote.model.TransactionRequestDto
import com.example.moneytalks.data.remote.model.TransactionResponseDto
import com.example.moneytalks.domain.repository.BaseRepository

class BaseRepositoryImpl(
    private val api: ApiService
): BaseRepository {
    override suspend fun getAccounts(): List<AccountDto> {
        return api.getAccounts()
    }

    override suspend fun getAccountById(id: Int): AccountResponseDto {
        return api.getAccountById(id)
    }

    override suspend fun createAccount(request: AccountCreateRequestDto): AccountDto {
        return api.createAccount(request)
    }

    override suspend fun updateAccount(
        id: Int,
        request: AccountUpdateRequestDto
    ): AccountDto {
        return api.updateAccountById(id, request)
    }

    override suspend fun deleteAccount(id: Int) {
        return api.deleteAccountById(id)
    }

    override suspend fun getAccountHistory(id: Int): AccountHistoryResponseDto {
        return api.getAccountHistory(id)
    }

    override suspend fun getCategories(): List<CategoryDto> {
        return api.getCategories()
    }

    override suspend fun getCategoriesByType(isIncome: Boolean): List<CategoryDto> {
        return api.getCategoriesByType(isIncome)
    }

    override suspend fun createTransaction(request: TransactionRequestDto): TransactionDto {
        return api.createTransaction(request)
    }

    override suspend fun getTransactionById(id: Int): TransactionResponseDto {
        return api.getTransactionById(id)
    }

    override suspend fun updateTransaction(
        id: Int,
        request: TransactionRequestDto
    ): TransactionResponseDto {
        return api.updateTransaction(id, request)
    }

    override suspend fun deleteTransaction(id: Int) {
        return api.deleteTransaction(id)
    }

    override suspend fun getTransactionsByPeriod(
        accountId: Int,
        startDate: String?,
        endDate: String?
    ): List<TransactionResponseDto> {
        return api.getTransactionsByPeriod(accountId, startDate, endDate)
    }
}