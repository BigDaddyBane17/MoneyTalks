package com.example.feature_expenses.usecase

import com.example.core.domain.models.Category
import com.example.core.domain.repository.CategoryRepository
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(isIncome: Boolean): List<Category> {
        return categoryRepository.getCategoriesByType(isIncome)
    }
} 