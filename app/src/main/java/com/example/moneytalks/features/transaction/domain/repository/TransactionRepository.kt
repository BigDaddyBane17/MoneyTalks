package com.example.moneytalks.features.transaction.domain.repository

import com.example.moneytalks.features.transaction.data.remote.model.TransactionDto
import com.example.moneytalks.features.transaction.data.remote.model.TransactionRequestDto
import com.example.moneytalks.features.transaction.data.remote.model.TransactionResponseDto

interface TransactionRepository {
    suspend fun createTransaction(request: TransactionRequestDto): TransactionDto
    suspend fun getTransactionById(id: Int): TransactionResponseDto
    suspend fun updateTransaction(id: Int, request: TransactionRequestDto): TransactionResponseDto
    suspend fun deleteTransaction(id: Int)
    suspend fun getTransactionsByPeriod(
        accountId: Int,
        startDate: String?,
        endDate: String?
    ): List<TransactionResponseDto>
}