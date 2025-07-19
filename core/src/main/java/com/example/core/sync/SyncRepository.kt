package com.example.core.sync

interface SyncRepository {

    suspend fun syncTransactions(): Int

    suspend fun hasUnsyncedTransactions(): Boolean
} 