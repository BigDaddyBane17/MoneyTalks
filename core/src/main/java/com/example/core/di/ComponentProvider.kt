package com.example.core.di

import com.example.core.data.dao.AccountDao
import com.example.core.data.dao.CategoryDao
import com.example.core.data.dao.TransactionDao
import com.example.core.domain.repository.AccountRepository
import com.example.core.domain.repository.CategoryRepository

interface ComponentProvider {
    fun provideApplicationComponent(): ApplicationComponent
}

// расширить если нужен будет репо аккаунтов
interface FeatureComponent : ApplicationComponent {
    fun accountRepository(): AccountRepository
    fun categoryRepository(): CategoryRepository
    fun transactionDao(): TransactionDao
    fun accountDao(): AccountDao
    fun categoryDao(): CategoryDao
}

interface FeatureComponentProvider {
    fun provideFeatureComponent(): FeatureComponent
} 