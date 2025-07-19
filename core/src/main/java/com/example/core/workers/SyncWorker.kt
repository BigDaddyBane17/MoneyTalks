package com.example.core.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.core.sync.SyncRepository
import javax.inject.Inject

class SyncWorker(
    context: Context,
    params: WorkerParameters,
    private val syncRepository: SyncRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        Log.d("SyncWorker", "SyncWorker запущен, ID: ${id}")
        
        return try {
            Log.d("SyncWorker", "Проверяем наличие несинхронизированных транзакций")
            val hasUnsynced = syncRepository.hasUnsyncedTransactions()
            Log.d("SyncWorker", "Есть несинхронизированные транзакции: $hasUnsynced")
            
            val syncedCount = syncRepository.syncTransactions()
            Log.d("SyncWorker", "Синхронизация завершена: $syncedCount транзакций")

            Result.success()
        } catch (e: Exception) {
            Log.e("SyncWorker", "Ошибка при синхронизации: ${e.message}", e)
            Result.retry()
        }
    }
} 