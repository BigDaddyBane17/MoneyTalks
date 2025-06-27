package com.example.moneytalks.features.categories.domain.repository

import com.example.moneytalks.features.categories.data.remote.model.CategoryDto

interface CategoryRepository {
    suspend fun getCategories(): List<CategoryDto>
    suspend fun getCategoriesByType(isIncome: Boolean): List<CategoryDto>
}
