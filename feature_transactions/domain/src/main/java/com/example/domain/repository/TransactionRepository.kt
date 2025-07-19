package com.example.domain.repository

import com.example.domain.models.Transaction
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface TransactionRepository {
    suspend fun getExpensesByDate(accountId: Int, date: LocalDate): Flow<List<Transaction>>
    suspend fun getIncomesByDate(accountId: Int, date: LocalDate): Flow<List<Transaction>>
    

    suspend fun getExpensesByDateRange(accountId: Int, startDate: LocalDate, endDate: LocalDate): Flow<List<Transaction>>
    suspend fun getIncomesByDateRange(accountId: Int, startDate: LocalDate, endDate: LocalDate): Flow<List<Transaction>>

    suspend fun createTransaction(
        accountId: Int,
        categoryId: Int,
        amount: String,
        transactionDate: String,
        comment: String?
    ): Result<Unit>
    

    suspend fun getTransactionById(transactionId: Int): Result<Transaction>
    

    suspend fun updateTransaction(
        transactionId: Int,
        accountId: Int,
        categoryId: Int,
        amount: String,
        transactionDate: String,
        comment: String?
    ): Result<Unit>

    suspend fun deleteTransaction(transactionId: Int): Result<Unit>

}