package com.example.core.sync

import android.content.Context
import android.util.Log
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.core.workers.SyncWorker
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SyncManager @Inject constructor(
    private val context: Context
) {

    private val workManager = WorkManager.getInstance(context)

    /**
     * Запускает периодическую синхронизацию каждые 2 часа при наличии интернета
     */
    fun startPeriodicSync() {
        Log.d("SyncManager", "Запускаем периодическую синхронизацию")
        
        try {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val syncWorkRequest = PeriodicWorkRequestBuilder<SyncWorker>(
                2, TimeUnit.HOURS
            )
                .setConstraints(constraints)
                .addTag("sync_worker")
                .build()

            workManager.enqueueUniquePeriodicWork(
                SYNC_WORK_NAME,
                ExistingPeriodicWorkPolicy.UPDATE,
                syncWorkRequest
            )
            
            Log.d("SyncManager", "Периодическая синхронизация запущена: $SYNC_WORK_NAME")
            
            // Проверяем статус задачи
            workManager.getWorkInfosForUniqueWorkLiveData(SYNC_WORK_NAME)
                .observeForever { workInfos ->
                    workInfos.forEach { workInfo ->
                        Log.d("SyncManager", "Статус задачи ${workInfo.id}: ${workInfo.state}")
                    }
                }
                
        } catch (e: Exception) {
            Log.e("SyncManager", "Ошибка при запуске синхронизации: ${e.message}", e)
        }
    }

    /**
     * Останавливает периодическую синхронизацию
     */
    fun stopPeriodicSync() {
        workManager.cancelUniqueWork(SYNC_WORK_NAME)
    }

    fun checkSyncStatus() {
        Log.d("SyncManager", "Проверка статуса синхронизации")
        
        workManager.getWorkInfosForUniqueWorkLiveData(SYNC_WORK_NAME)
            .observeForever { workInfos ->
                if (workInfos.isEmpty()) {
                    Log.w("SyncManager", "Нет активных задач синхронизации")
                } else {
                    workInfos.forEach { workInfo ->
                        Log.d("SyncManager", "Задача ${workInfo.id}: ${workInfo.state}")
                    }
                }
            }
    }

    companion object {
        private const val SYNC_WORK_NAME = "transaction_sync_work"
    }
} 