package com.example.moneytalks.presentation.item_expenses

import com.example.moneytalks.domain.model.ExpenseItem

sealed class ItemExpenseUiState {
    object Loading: ItemExpenseUiState()
    data class Success(val items: List<ExpenseItem>, val filteredExpenses: List<ExpenseItem>, val searchQuery: String = "") : ItemExpenseUiState()
    data class Error(val message: String): ItemExpenseUiState()
}