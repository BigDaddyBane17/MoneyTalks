package com.example.core.domain.repository

import com.example.core.domain.models.Category

interface CategoryRepository {
    suspend fun getCategories(): List<Category>
    suspend fun getCategoriesByType(isIncome: Boolean): List<Category>
} 