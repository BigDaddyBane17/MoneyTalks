package com.example.moneytalks.presentation.spendings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneytalks.data.remote.model.TransactionRequest
import com.example.moneytalks.domain.model.Expenses
import com.example.moneytalks.domain.repository.BaseRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SpendingViewModel(
    private val repository: BaseRepository,
    private val type: String,
): ViewModel() {

    private val _uiState = MutableStateFlow<SpendingUiState>(SpendingUiState.Loading)
    val uiState: StateFlow<SpendingUiState> = _uiState.asStateFlow()

    fun handleIntent(intent: SpendingIntent) {
        when (intent) {
            is SpendingIntent.LoadExpenses -> loadData(intent.accountId ?: 1, intent.startDate, intent.endDate)
            is SpendingIntent.OnItemClicked -> handleItemClick()
        }
    }



    fun loadData(accountId: Int, startDate: String, endDate: String) {
        viewModelScope.launch {
            _uiState.value = SpendingUiState.Loading
            try {
                val spendingList = repository.getTransactionsByPeriod(
                    accountId, startDate, endDate
                ).filter {
                    /*!it.category.isIncome*/
                    if (type == "расходы") !it.category.isIncome else it.category.isIncome
                } // сделать маппер тут
                val total = spendingList.sumOf { it.amount.toDouble() }

                _uiState.value = SpendingUiState.Success(spendingList, "$total ₽") // сделать зависимость от валюты
            } catch (e: Exception) {
                _uiState.value = SpendingUiState.Error("Ошибка: ${e.message}")
            }
        }
    }

    private fun handleItemClick() {
        //TODO
    }


}


