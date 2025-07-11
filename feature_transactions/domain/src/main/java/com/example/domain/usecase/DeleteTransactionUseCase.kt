package com.example.domain.usecase

import com.example.domain.repository.TransactionRepository
import javax.inject.Inject

class DeleteTransactionUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {
    suspend operator fun invoke(transactionId: Int): Result<Unit> {
        return transactionRepository.deleteTransaction(transactionId)
    }
} 