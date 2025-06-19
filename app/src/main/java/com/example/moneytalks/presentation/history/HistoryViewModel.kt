package com.example.moneytalks.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneytalks.domain.repository.BaseRepository
import com.example.moneytalks.presentation.spendings.SpendingUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val repository: BaseRepository
): ViewModel() {
    private val _uiState = MutableStateFlow<HistoryUiState>(HistoryUiState.Loading)
    val uiState: StateFlow<HistoryUiState> = _uiState.asStateFlow()


    fun handleIntent(intent: HistoryIntent, isIncome: Boolean) {
        when(intent) {
            is HistoryIntent.LoadHistory -> loadHistory(intent.accountId ?: 1, intent.startDate, intent.endDate, isIncome)
        }
    }

    fun loadHistory(accountId: Int, startDate: String, endDate: String, isIncome: Boolean) {
        viewModelScope.launch {
            _uiState.value = HistoryUiState.Loading

            try {
                //delay(2000)
                val historyList = repository.getTransactionsByPeriod(
                    accountId, startDate, endDate
                ).filter {
                    it.category.isIncome == isIncome
                }
                val total = historyList.sumOf { it.amount.toDouble() }
                _uiState.value = HistoryUiState.Success(historyList, "%,.2f ₽".format(total))
            }
            catch (e: Exception) {
                _uiState.value = HistoryUiState.Error(
                    "Ошибка: ${e.message}"
                )
            }
        }
    }

}