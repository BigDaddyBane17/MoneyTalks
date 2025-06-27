package com.example.moneytalks.features.transaction.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneytalks.core.network.NetworkMonitor
import com.example.moneytalks.core.network.retryIO
import com.example.moneytalks.features.transaction.domain.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: TransactionRepository,
    private val networkMonitor: NetworkMonitor
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

            if (!networkMonitor.isConnected.value) {
                _uiState.value = HistoryUiState.Error("Нет соединения с интернетом")
                return@launch
            }

            try {
                val historyList = retryIO(times = 3, delayMillis = 2000) {
                    repository.getTransactionsByPeriod(
                        accountId, startDate, endDate
                    ).filter {
                        it.category.isIncome == isIncome
                    }
                }
                val total = historyList.sumOf { it.amount.toDouble() }
                _uiState.value = HistoryUiState.Success(historyList, "%,.2f ₽".format(total))
            }
            catch (e: IOException) {
                _uiState.value = HistoryUiState.Error("Нет соединения с интернетом")
            }
            catch (e: Exception) {
                _uiState.value = HistoryUiState.Error(
                    "Ошибка: ${e.localizedMessage}"
                )
            }
        }
    }


}