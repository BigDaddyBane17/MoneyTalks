package com.example.core.usecase

import com.example.core.sync.SyncRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SyncTransactionsUseCase @Inject constructor(
    private val syncRepository: SyncRepository
) {

    suspend fun execute(): Int {
        return syncRepository.syncTransactions()
    }

    suspend fun hasUnsyncedTransactions(): Boolean {
        return syncRepository.hasUnsyncedTransactions()
    }
} 