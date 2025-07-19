package com.example.core.sync

import android.util.Log
import com.example.core.data.dao.AccountDao
import com.example.core.data.dao.CategoryDao
import com.example.core.data.dao.TransactionDao
import com.example.core.data.entities.TransactionEntity
import com.example.core.network.TransactionApiService
import com.example.core.network.TransactionRequestDto
import com.example.core.network.toEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SyncRepositoryImpl @Inject constructor(
    private val transactionDao: TransactionDao,
    private val apiService: TransactionApiService
) : SyncRepository {

    override suspend fun syncTransactions(): Int {
        Log.d("SyncRepository", "Начинаем синхронизацию транзакций")
        var syncedCount = 0
        
        val unsyncedTransactions = transactionDao.getUnsyncedTransactions()
        Log.d("SyncRepository", "Найдено несинхронизированных транзакций: ${unsyncedTransactions.size}")
        
        if (unsyncedTransactions.isEmpty()) {
            Log.d("SyncRepository", "Нет транзакций для синхронизации")
            return 0
        }
        
        for (transaction in unsyncedTransactions) {
            Log.d("SyncRepository", "Обрабатываем транзакцию: ID=${transaction.id}, isSynced=${transaction.isSynced}, isDeleted=${transaction.isDeleted}")
            try {
                                    when {
                        transaction.id < 0 -> {
                            Log.d("SyncRepository", "Создаем новую транзакцию на сервере: ID=${transaction.id}")
                            Log.d("SyncRepository", "Данные транзакции: accountId=${transaction.accountId}, amount=${transaction.amount}, date=${transaction.transactionDate}")
                            
                            val request = TransactionRequestDto(
                                accountId = transaction.accountId,
                                categoryId = transaction.categoryId,
                                amount = transaction.amount,
                                transactionDate = transaction.transactionDate,
                                comment = transaction.comment
                            )
                            
                            Log.d("SyncRepository", "Отправляем запрос на сервер...")
                            val response = apiService.createTransaction(request)
                            Log.d("SyncRepository", "Получен ответ от сервера: ID=${response.id}")
                            
                            val syncedEntity = response.toEntity(
                                accountName = transaction.accountName,
                                categoryName = transaction.categoryName,
                                categoryEmoji = transaction.categoryEmoji,
                                isIncome = transaction.isIncome
                            )
                            
                            transactionDao.deleteById(transaction.id)
                            transactionDao.insert(syncedEntity)
                            syncedCount++
                            Log.d("SyncRepository", "Транзакция успешно синхронизирована: локальный ID=${transaction.id} -> серверный ID=${response.id}")
                        }
                    
                    !transaction.isSynced && !transaction.isDeleted -> {
                        Log.d("SyncRepository", "Обновляем транзакцию на сервере: ID=${transaction.id}")
                        val request = TransactionRequestDto(
                            accountId = transaction.accountId,
                            categoryId = transaction.categoryId,
                            amount = transaction.amount,
                            transactionDate = transaction.transactionDate,
                            comment = transaction.comment
                        )
                        
                        val response = apiService.updateTransaction(transaction.id, request)
                        val syncedEntity = response.toEntity()
                        transactionDao.insert(syncedEntity)
                        syncedCount++
                        Log.d("SyncRepository", "Транзакция обновлена на сервере: ID=${transaction.id}")
                    }
                    
                    transaction.isDeleted && !transaction.isSynced -> {
                        Log.d("SyncRepository", "Удаляем транзакцию на сервере: ID=${transaction.id}")
                        apiService.deleteTransaction(transaction.id)
                        transactionDao.insert(transaction.copy(isSynced = true))
                        syncedCount++
                        Log.d("SyncRepository", "Транзакция удалена на сервере: ID=${transaction.id}")
                    }
                }
            } catch (e: Exception) {
                Log.e("SyncRepository", "Ошибка при синхронизации транзакции ID=${transaction.id}: ${e.message}", e)
            }
        }
        
        Log.d("SyncRepository", "Синхронизация завершена. Всего синхронизировано: $syncedCount")
        return syncedCount
    }

    override suspend fun hasUnsyncedTransactions(): Boolean {
        val count = transactionDao.getUnsyncedTransactionsCount()
        Log.d("SyncRepository", "Проверка несинхронизированных транзакций: $count")
        return count > 0
    }
} 