package com.example.data.repository

import com.example.data.api.TransactionApiService
import com.example.data.mappers.TransactionMapper
import com.example.data.models.TransactionRequestDto
import com.example.domain.models.Transaction
import com.example.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val apiService: TransactionApiService,
    private val mapper: TransactionMapper
) : TransactionRepository {

    override suspend fun createTransaction(
        accountId: Int,
        categoryId: Int,
        amount: String,
        transactionDate: String,
        comment: String?
    ): Result<Unit> {
        return try {
            val request = TransactionRequestDto(
                accountId = accountId,
                categoryId = categoryId,
                amount = amount,
                transactionDate = transactionDate,
                comment = comment
            )
            apiService.createTransaction(request)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getTransactionById(transactionId: Int): Result<Transaction> {
        return try {
            val transactionDto = apiService.getTransactionById(transactionId)
            val transaction = mapper.toDomain(transactionDto)
            Result.success(transaction)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateTransaction(
        transactionId: Int,
        accountId: Int,
        categoryId: Int,
        amount: String,
        transactionDate: String,
        comment: String?
    ): Result<Unit> {
        return try {
            val request = TransactionRequestDto(
                accountId = accountId,
                categoryId = categoryId,
                amount = amount,
                transactionDate = transactionDate,
                comment = comment
            )
            apiService.updateTransaction(transactionId, request)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteTransaction(transactionId: Int): Result<Unit> {
        return try {
            apiService.deleteTransaction(transactionId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getExpensesByDate(accountId: Int, date: LocalDate): Flow<List<Transaction>> = flow {
        try {
            val startDate = date.format(DateTimeFormatter.ISO_LOCAL_DATE)
            val endDate = date.format(DateTimeFormatter.ISO_LOCAL_DATE)
            
            val transactionsDto = apiService.getTransactionsByPeriod(
                accountId = accountId,
                startDate = startDate,
                endDate = endDate
            )

            val transactions = transactionsDto
                .filter { !it.category.isIncome }
                .map { mapper.toDomain(it) }
            
            emit(transactions)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }

    override suspend fun getIncomesByDate(accountId: Int, date: LocalDate): Flow<List<Transaction>> = flow {
        try {
            val startDate = date.format(DateTimeFormatter.ISO_LOCAL_DATE)
            val endDate = date.format(DateTimeFormatter.ISO_LOCAL_DATE)
            
            val transactionsDto = apiService.getTransactionsByPeriod(
                accountId = accountId,
                startDate = startDate,
                endDate = endDate
            )

            val transactions = transactionsDto
                .filter { it.category.isIncome }
                .map { mapper.toDomain(it) }
            
            emit(transactions)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }

    override suspend fun getExpensesByDateRange(accountId: Int, startDate: LocalDate, endDate: LocalDate): Flow<List<Transaction>> = flow {
        try {
            val start = startDate.format(DateTimeFormatter.ISO_LOCAL_DATE)
            val end = endDate.format(DateTimeFormatter.ISO_LOCAL_DATE)
            
            val transactionsDto = apiService.getTransactionsByPeriod(
                accountId = accountId,
                startDate = start,
                endDate = end
            )

            val transactions = transactionsDto
                .filter { !it.category.isIncome }
                .map { mapper.toDomain(it) }
            
            emit(transactions)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }

    override suspend fun getIncomesByDateRange(accountId: Int, startDate: LocalDate, endDate: LocalDate): Flow<List<Transaction>> = flow {
        try {
            val start = startDate.format(DateTimeFormatter.ISO_LOCAL_DATE)
            val end = endDate.format(DateTimeFormatter.ISO_LOCAL_DATE)
            
            val transactionsDto = apiService.getTransactionsByPeriod(
                accountId = accountId,
                startDate = start,
                endDate = end
            )
            
            // Filter by category type: incomes have isIncome = true
            val transactions = transactionsDto
                .filter { it.category.isIncome }
                .map { mapper.toDomain(it) }
            
            emit(transactions)
        } catch (e: Exception) {
            // При сетевых ошибках возвращаем пустой список
            emit(emptyList())
        }
    }
} 