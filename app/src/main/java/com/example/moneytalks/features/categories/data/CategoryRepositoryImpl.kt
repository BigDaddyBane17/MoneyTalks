package com.example.moneytalks.features.categories.data

import com.example.moneytalks.features.categories.data.remote.CategoryApiService
import com.example.moneytalks.features.categories.domain.repository.CategoryRepository

class CategoryRepositoryImpl(
    private val api: CategoryApiService
): CategoryRepository {
    override suspend fun getCategories() = api.getCategories()
    override suspend fun getCategoriesByType(isIncome: Boolean) = api.getCategoriesByType(isIncome)
}
