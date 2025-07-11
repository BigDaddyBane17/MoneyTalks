package com.example.core.di

import com.example.core.domain.repository.AccountRepository
import com.example.core.domain.repository.CategoryRepository

interface ComponentProvider {
    fun provideApplicationComponent(): ApplicationComponent
}

// расширить если нужен будет репо аккаунтов
interface FeatureComponent : ApplicationComponent {
    fun accountRepository(): AccountRepository
    fun categoryRepository(): CategoryRepository
}

interface FeatureComponentProvider {
    fun provideFeatureComponent(): FeatureComponent
} 