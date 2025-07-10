package com.example.data.repository

import com.example.data.api.TransactionApiService
import com.example.data.mappers.TransactionMapper
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

    override suspend fun getExpensesByDate(accountId: Int, date: LocalDate): Flow<List<Transaction>> = flow {
        val startDate = date.format(DateTimeFormatter.ISO_LOCAL_DATE)
        val endDate = date.format(DateTimeFormatter.ISO_LOCAL_DATE)
        
        val transactionsDto = apiService.getTransactionsByPeriod(
            accountId = accountId,
            startDate = startDate,
            endDate = endDate
        )
        
        // Фильтруем только расходы (категории с isIncome = false)
        val expenses = transactionsDto
            .filter { !it.category.isIncome }
            .map { mapper.toDomain(it) }
        
        emit(expenses)
    }
} 