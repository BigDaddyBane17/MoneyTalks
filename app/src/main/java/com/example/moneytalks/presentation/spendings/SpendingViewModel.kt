package com.example.moneytalks.presentation.spendings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneytalks.domain.model.Expenses
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SpendingViewModel: ViewModel() {

    private val _uiState = MutableStateFlow<SpendingUiState>(SpendingUiState.Loading)
    val uiState: StateFlow<SpendingUiState> = _uiState.asStateFlow()

    fun handleIntent(intent: SpendingIntent) {
        when (intent) {
            is SpendingIntent.LoadExpenses -> loadData()
            is SpendingIntent.OnItemClicked -> handleItemClick()
            is SpendingIntent.AddExpense -> addExpense()
            is SpendingIntent.GoToHistory -> goToHistory()
        }
    }

    fun loadData() {
        viewModelScope.launch {
            _uiState.value = SpendingUiState.Loading
            try {
                delay(300)
                val expensesList = listOf(
                    Expenses(
                        id = 1,
                        leadIcon = "\uD83C\uDFE1",
                        title = "Аренда квартиры",
                        description = null,
                        amount = "100 000",
                        currency = "₽"
                    ),
                    Expenses(
                        id = 2,
                        leadIcon = "\uD83D\uDC57",
                        title = "Одежда",
                        description = null,
                        amount = "100 000",
                        currency = "₽"
                    ),
                    Expenses(
                        id = 3,
                        leadIcon = "\uD83D\uDC36",
                        title = "На собачку",
                        description = "Джек",
                        amount = "100 000",
                        currency = "₽"
                    ),
                    Expenses(
                        id = 4,
                        leadIcon = "\uD83D\uDC36",
                        title = "На собачку",
                        description = "Энни",
                        amount = "100 000",
                        currency = "₽"
                    ),
                    Expenses(
                        id = 5,
                        leadIcon = "\uD83C\uDFE0",
                        title = "Ремонт квартиры",
                        description = null,
                        amount = "100 000",
                        currency = "₽"
                    ),
                    Expenses(
                        id = 6,
                        leadIcon = "\uD83C\uDF6D",
                        title = "Продукты",
                        description = null,
                        amount = "100 000",
                        currency = "₽"
                    ),
                    Expenses(
                        id = 7,
                        leadIcon = "\uD83C\uDFCB",
                        title = "Спортзал",
                        description = null,
                        amount = "100 000",
                        currency = "₽"
                    ),
                    Expenses(
                        id = 8,
                        leadIcon = "\uD83D\uDC8A",
                        title = "Медицина",
                        description = null,
                        amount = "100 000",
                        currency = "₽"
                    )
                )
                _uiState.value = SpendingUiState.Success(expensesList, "436 558 ₽")
            } catch (e: Exception) {
                _uiState.value = SpendingUiState.Error("Ошибка: ${e.message}")
            }
        }
    }

    private fun handleItemClick() {
        //TODO
    }

    private fun addExpense() {
        //TODO
    }

    private fun goToHistory() {
        //TODO
    }

}


