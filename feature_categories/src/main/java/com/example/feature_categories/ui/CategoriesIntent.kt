package com.example.feature_categories.ui

sealed interface CategoryIntent {
    data object LoadCategories : CategoryIntent
    data class SearchCategory(val query: String) : CategoryIntent
    data object Refresh : CategoryIntent
}