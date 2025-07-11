package com.example.core.di

import com.example.core.domain.repository.AccountRepository

interface ComponentProvider {
    fun provideApplicationComponent(): ApplicationComponent
}

// расширить если нужен будет репо аккаунтов
interface FeatureComponent : ApplicationComponent {
    fun accountRepository(): AccountRepository
}

interface FeatureComponentProvider {
    fun provideFeatureComponent(): FeatureComponent
} 