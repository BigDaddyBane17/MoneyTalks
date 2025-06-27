package com.example.moneytalks.features.categories.presentation.item_expenses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneytalks.features.categories.domain.model.ExpenseItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class ItemExpensesViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<ItemExpenseUiState>(ItemExpenseUiState.Loading)
    val uiState: StateFlow<ItemExpenseUiState> = _uiState.asStateFlow()


    private val allExpenses = listOf(
        ExpenseItem(1, "\uD83C\uDFE1", "Аренда квартиры"),
        ExpenseItem(2, "\uD83D\uDC57", "Одежда"),
        ExpenseItem(3, "\uD83D\uDC36", "На собачку"),
        ExpenseItem(4, "\uD83D\uDC36", "На собачку"),
        ExpenseItem(5, "\uD83C\uDFE0", "Ремонт квартиры"),
        ExpenseItem(6, "\uD83C\uDF6D", "Продукты"),
        ExpenseItem(7, "\uD83C\uDFCB", "Спортзал"),
        ExpenseItem(8, "\uD83D\uDC8A", "Медицина")
    )


    fun handleIntent(intent: ItemExpensesIntent) {
        when (intent) {
            is ItemExpensesIntent.LoadItemExpenses -> loadItemExpenses()
            is ItemExpensesIntent.SearchItemExpenses -> searchItemExpenses(intent.query)
        }
    }

    private fun loadItemExpenses() {

        viewModelScope.launch {
            delay(300)
            _uiState.value = ItemExpenseUiState.Success(
                items = allExpenses,
                filteredExpenses = allExpenses
            )
        }

    }

    private fun searchItemExpenses(query: String) {
        //TODO
    }

}