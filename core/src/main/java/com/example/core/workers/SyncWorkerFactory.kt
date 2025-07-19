package com.example.core.workers

import android.content.Context
import android.util.Log
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.core.sync.SyncRepository
import javax.inject.Inject
import javax.inject.Provider

class SyncWorkerFactory @Inject constructor(
    private val syncRepositoryProvider: Provider<SyncRepository>
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        Log.d("SyncWorkerFactory", "Создаем worker: $workerClassName")
        
        return when (workerClassName) {
            SyncWorker::class.java.name -> {
                Log.d("SyncWorkerFactory", "SyncWorker создан")
                SyncWorker(appContext, workerParameters, syncRepositoryProvider.get())
            }
            else -> {
                Log.w("SyncWorkerFactory", "Неизвестный worker: $workerClassName")
                null
            }
        }
    }
} 