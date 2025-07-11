package com.example.domain.usecase

import com.example.domain.models.Transaction
import com.example.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class GetTodayExpensesUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(accountId: Int): Flow<List<Transaction>> {
        return repository.getExpensesByDate(accountId, LocalDate.now())
    }
} 