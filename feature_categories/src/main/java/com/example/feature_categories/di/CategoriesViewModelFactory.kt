package com.example.feature_categories.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.feature_categories.ui.CategoryViewModel
import javax.inject.Inject
import javax.inject.Provider

class CategoriesViewModelFactory @Inject constructor(
    private val categoryViewModelProvider: Provider<CategoryViewModel>
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            CategoryViewModel::class.java -> categoryViewModelProvider.get() as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
        }
    }
}