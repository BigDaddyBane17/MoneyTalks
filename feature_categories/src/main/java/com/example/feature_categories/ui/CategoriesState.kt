package com.example.feature_categories.ui

import com.example.core.domain.models.Category

data class CategoryUiState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val categories: List<Category> = emptyList(),
    val searchQuery: String = ""
)