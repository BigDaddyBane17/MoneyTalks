package com.example.feature_categories.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CategoryViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CategoryUiState())
    val uiState: StateFlow<CategoryUiState> = _uiState.asStateFlow()

    private var allCategories: List<com.example.core.domain.models.Category> = emptyList()

    init {
        handleIntent(CategoryIntent.LoadCategories)
    }

    fun handleIntent(intent: CategoryIntent) {
        when (intent) {
            is CategoryIntent.LoadCategories -> loadCategories()
            is CategoryIntent.SearchCategory -> searchCategories(intent.query)
            is CategoryIntent.Refresh -> loadCategories()
        }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                val categories = categoryRepository.getCategories()
                allCategories = categories
                applySearch(_uiState.value.searchQuery)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Ошибка загрузки категорий"
                )
            }
        }
    }

    private fun searchCategories(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
        applySearch(query)
    }

    private fun applySearch(query: String) {
        val filteredCategories = if (query.isBlank()) {
            allCategories
        } else {
            allCategories.filter { category ->
                category.name.contains(query, ignoreCase = true)
            }
        }
        _uiState.value = _uiState.value.copy(
            isLoading = false,
            error = null,
            categories = filteredCategories
        )
    }
}