package com.example.moneytalks.features.categories.presentation.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneytalks.core.network.NetworkMonitor
import com.example.moneytalks.core.network.retryIO
import com.example.moneytalks.features.categories.data.remote.model.CategoryDto
import com.example.moneytalks.features.categories.domain.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * ViewModel для управления состоянием экрана категорий.
 */

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val repository: CategoryRepository,
    private val networkMonitor: NetworkMonitor
) : ViewModel() {

    private var allCategories: List<CategoryDto> = emptyList()

    private val _uiState = MutableStateFlow<CategoryUiState>(CategoryUiState.Loading)
    val uiState: StateFlow<CategoryUiState> = _uiState.asStateFlow()

    private var searchJob: Job? = null

    init {
        handleIntent(CategoryIntent.LoadCategory)
    }

    fun handleIntent(intent: CategoryIntent) {
        when (intent) {
            is CategoryIntent.LoadCategory -> {
                viewModelScope.launch {
                    _uiState.value = CategoryUiState.Loading
                    if (!networkMonitor.isConnected.value) {
                        _uiState.value = CategoryUiState.Error("Нет соединения с интернетом")
                        return@launch
                    }
                    try {
                        allCategories = retryIO(times = 3, delayMillis = 2000) {
                            repository.getCategories()
                        }
                        _uiState.value = CategoryUiState.Success(allCategories)
                    } catch (e: Exception) {
                        _uiState.value = CategoryUiState.Error(
                            e.message?.let { "Ошибка: $it" }
                                ?: "Неизвестная ошибка"
                        )
                    }
                }
            }
            is CategoryIntent.SearchCategory -> {
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(300)
                    val query = intent.query.trim()
                    val filtered = if (query.isEmpty()) {
                        allCategories
                    } else {
                        allCategories.filter {
                            it.name.contains(query, ignoreCase = true)
                        }
                    }
                    _uiState.value = CategoryUiState.Success(filtered)
                }
            }
        }
    }
}

