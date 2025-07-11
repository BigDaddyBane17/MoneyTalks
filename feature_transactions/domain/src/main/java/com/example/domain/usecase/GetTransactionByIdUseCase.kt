package com.example.domain.usecase

import com.example.domain.models.Transaction
import com.example.domain.repository.TransactionRepository
import javax.inject.Inject

class GetTransactionByIdUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {
    suspend operator fun invoke(transactionId: Int): Result<Transaction> {
        return transactionRepository.getTransactionById(transactionId)
    }
} 