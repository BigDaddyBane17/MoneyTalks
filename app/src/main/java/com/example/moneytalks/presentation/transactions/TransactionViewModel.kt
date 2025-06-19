package com.example.moneytalks.presentation.transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneytalks.domain.repository.BaseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TransactionViewModel(
    private val repository: BaseRepository,
    private val type: String,
): ViewModel() {

    private val _uiState = MutableStateFlow<TransactionUiState>(TransactionUiState.Loading)
    val uiState: StateFlow<TransactionUiState> = _uiState.asStateFlow()

    fun handleIntent(intent: TransactionIntent) {
        when (intent) {
            is TransactionIntent.LoadExpenses -> loadData(intent.accountId ?: 1, intent.startDate, intent.endDate)
            is TransactionIntent.OnItemClicked -> handleItemClick()
        }
    }

    fun loadData(accountId: Int, startDate: String, endDate: String) {
        viewModelScope.launch {
            _uiState.value = TransactionUiState.Loading
            try {
                val spendingList = repository.getTransactionsByPeriod(
                    accountId, startDate, endDate
                ).filter {
                    if (type == "расходы") !it.category.isIncome else it.category.isIncome
                } // сделать маппер тут
                val total = spendingList.sumOf { it.amount.toDouble() }

                _uiState.value = TransactionUiState.Success(spendingList, "$total ₽") // сделать зависимость от валюты
            } catch (e: Exception) {
                _uiState.value = TransactionUiState.Error("Ошибка: ${e.message}")
            }
        }
    }

    private fun handleItemClick() {
        //TODO
    }


}


