package com.example.core.di

import com.example.core.sync.SyncRepository
import com.example.core.sync.SyncRepositoryImpl
import com.example.core.workers.SyncWorkerFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object WorkManagerModule {

    @Provides
    @Singleton
    fun provideSyncWorkerFactory(
        syncRepositoryProvider: javax.inject.Provider<SyncRepository>
    ): SyncWorkerFactory {
        return SyncWorkerFactory(syncRepositoryProvider)
    }
}

@Module
abstract class SyncModule {

    @Binds
    @Singleton
    abstract fun bindSyncRepository(
        syncRepositoryImpl: SyncRepositoryImpl
    ): SyncRepository
} 