package com.example.moneytalks.features.categories.presentation.category

import com.example.moneytalks.features.categories.data.remote.model.CategoryDto
import com.example.moneytalks.features.categories.domain.model.Category

sealed class CategoryUiState {
    object Loading: CategoryUiState()
    data class Success(val items: List<CategoryDto>) : CategoryUiState()
    data class Error(val message: String): CategoryUiState()
}
