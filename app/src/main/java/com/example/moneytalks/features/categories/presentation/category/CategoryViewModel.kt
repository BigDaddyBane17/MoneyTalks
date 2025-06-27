package com.example.moneytalks.features.categories.presentation.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneytalks.features.categories.domain.model.Category
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


/**
 * ViewModel для управления состоянием экрана категорий.
 */

class CategoryViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<CategoryUiState>(CategoryUiState.Loading)
    val uiState: StateFlow<CategoryUiState> = _uiState.asStateFlow()


    private val allExpenses = listOf(
        Category(1, "\uD83C\uDFE1", "Аренда квартиры", true),
        Category(2, "\uD83D\uDC57", "Одежда", true),
        Category(3, "\uD83D\uDC36", "На собачку", true),
        Category(4, "\uD83D\uDC36", "На собачку", true),
        Category(5, "\uD83C\uDFE0", "Ремонт квартиры", true),
        Category(6, "\uD83C\uDF6D", "Продукты", true),
        Category(7, "\uD83C\uDFCB", "Спортзал", true),
        Category(8, "\uD83D\uDC8A", "Медицина", true)
    )


    fun handleIntent(intent: CategoryIntent) {
        when (intent) {
            is CategoryIntent.LoadCategory -> loadItemExpenses()
            is CategoryIntent.SearchCategory -> searchItemExpenses(intent.query)
        }
    }

    private fun loadItemExpenses() {

        viewModelScope.launch {
            delay(300)
            _uiState.value = CategoryUiState.Success(
                items = allExpenses,
                filteredExpenses = allExpenses
            )
        }

    }

    private fun searchItemExpenses(query: String) {
        //TODO
    }

}
