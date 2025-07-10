package com.example.domain.repository

import com.example.domain.models.Transaction
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface TransactionRepository {
    suspend fun getExpensesByDate(accountId: Int, date: LocalDate): Flow<List<Transaction>>
}