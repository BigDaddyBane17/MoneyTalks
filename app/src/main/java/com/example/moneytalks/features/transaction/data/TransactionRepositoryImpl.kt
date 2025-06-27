package com.example.moneytalks.features.transaction.data

import com.example.moneytalks.features.transaction.data.remote.TransactionApiService
import com.example.moneytalks.features.transaction.data.remote.model.TransactionRequestDto
import com.example.moneytalks.features.transaction.domain.repository.TransactionRepository

class TransactionRepositoryImpl(
    private val api: TransactionApiService
): TransactionRepository {
    override suspend fun createTransaction(request: TransactionRequestDto) = api.createTransaction(request)
    override suspend fun getTransactionById(id: Int) = api.getTransactionById(id)
    override suspend fun updateTransaction(id: Int, request: TransactionRequestDto) = api.updateTransaction(id, request)
    override suspend fun deleteTransaction(id: Int) = api.deleteTransaction(id)
    override suspend fun getTransactionsByPeriod(
        accountId: Int, startDate: String?, endDate: String?
    ) = api.getTransactionsByPeriod(accountId, startDate, endDate)
}