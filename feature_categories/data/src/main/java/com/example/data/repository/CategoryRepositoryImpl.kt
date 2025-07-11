package com.example.data.repository

import com.example.core.domain.models.Category
import com.example.core.domain.repository.CategoryRepository
import com.example.data.api.CategoryApiService
import com.example.data.mappers.CategoryMapper
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val apiService: CategoryApiService,
    private val mapper: CategoryMapper
) : CategoryRepository {
    
    override suspend fun getCategories(): List<Category> {
        return apiService.getCategories().map { mapper.toDomain(it) }
    }
    
    override suspend fun getCategoriesByType(isIncome: Boolean): List<Category> {
        return apiService.getCategoriesByType(isIncome).map { mapper.toDomain(it) }
    }
}