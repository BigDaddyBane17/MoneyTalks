package com.example.data.repository

import com.example.core.data.dao.CategoryDao
import com.example.core.domain.models.Category
import com.example.core.domain.repository.CategoryRepository
import com.example.data.api.CategoryApiService
import com.example.data.mappers.toDomain
import com.example.data.mappers.toEntity
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val apiService: CategoryApiService,
    private val categoryDao: CategoryDao,
) : CategoryRepository {

    override suspend fun getCategories(): List<Category> {
        val cached = categoryDao.getAll().map { it.toDomain() }
        return try {
            val remote = apiService.getCategories()
            categoryDao.insertAll(remote.map { it.toEntity() })
            remote.map { it.toDomain() }
        } catch (e: Exception) {
            cached
        }
    }

    override suspend fun getCategoriesByType(isIncome: Boolean): List<Category> {
        val cached = categoryDao.getByType(isIncome).map { it.toDomain() }
        return try {
            val remote = apiService.getCategoriesByType(isIncome)
            categoryDao.insertAll(remote.map { it.toEntity() })
            remote.map { it.toDomain() }
        } catch (e: Exception) {
            cached
        }
    }


}
