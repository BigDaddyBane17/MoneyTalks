package com.example.domain.repository

import com.example.domain.models.Transaction
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface TransactionRepository {
    suspend fun getExpensesByDate(accountId: Int, date: LocalDate): Flow<List<Transaction>>
    suspend fun getIncomesByDate(accountId: Int, date: LocalDate): Flow<List<Transaction>>
    
    // For history screens with date range
    suspend fun getExpensesByDateRange(accountId: Int, startDate: LocalDate, endDate: LocalDate): Flow<List<Transaction>>
    suspend fun getIncomesByDateRange(accountId: Int, startDate: LocalDate, endDate: LocalDate): Flow<List<Transaction>>

    // Create transaction
    suspend fun createTransaction(
        accountId: Int,
        categoryId: Int,
        amount: String,
        transactionDate: String,
        comment: String?
    ): Result<Unit>
    
    // Get transaction by ID
    suspend fun getTransactionById(transactionId: Int): Result<Transaction>
    
    // Update transaction
    suspend fun updateTransaction(
        transactionId: Int,
        accountId: Int,
        categoryId: Int,
        amount: String,
        transactionDate: String,
        comment: String?
    ): Result<Unit>
    
    // Delete transaction
    suspend fun deleteTransaction(transactionId: Int): Result<Unit>
}