package com.example.moneytalks.features.categories.presentation.category

import com.example.moneytalks.features.categories.domain.model.Category

sealed class CategoryUiState {
    object Loading: CategoryUiState()
    data class Success(val items: List<Category>, val filteredExpenses: List<Category>, val searchQuery: String = "") : CategoryUiState()
    data class Error(val message: String): CategoryUiState()
}
