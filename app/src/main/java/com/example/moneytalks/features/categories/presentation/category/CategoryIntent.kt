package com.example.moneytalks.features.categories.presentation.category

sealed class CategoryIntent {
    object LoadCategory: CategoryIntent()
    data class SearchCategory(val query: String): CategoryIntent()
}
