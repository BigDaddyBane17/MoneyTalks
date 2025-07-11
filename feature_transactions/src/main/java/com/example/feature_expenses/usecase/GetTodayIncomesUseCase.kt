package com.example.feature_expenses.usecase

import com.example.domain.models.Transaction
import com.example.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class GetTodayIncomesUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {
    suspend operator fun invoke(accountId: Int): Flow<List<Transaction>> {
        val today = LocalDate.now()
        return transactionRepository.getIncomesByDate(accountId, today)
    }
} 