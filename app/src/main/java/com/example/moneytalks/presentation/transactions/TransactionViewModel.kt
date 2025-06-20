package com.example.moneytalks.presentation.transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneytalks.domain.repository.BaseRepository
import com.example.moneytalks.network.NetworkMonitor
import com.example.moneytalks.network.retryIO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class TransactionViewModel(
    private val repository: BaseRepository,
    private val networkMonitor: NetworkMonitor
): ViewModel() {


    private val _uiState = MutableStateFlow<TransactionUiState>(TransactionUiState.Loading)
    val uiState: StateFlow<TransactionUiState> = _uiState.asStateFlow()

    fun handleIntent(intent: TransactionIntent, isIncome: Boolean) {
        when (intent) {
            is TransactionIntent.LoadExpenses -> loadData(intent.accountId ?: 1, intent.startDate, intent.endDate, isIncome)
            is TransactionIntent.OnItemClicked -> handleItemClick()
        }
    }

    fun loadData(accountId: Int, startDate: String, endDate: String, isIncome: Boolean) {
        viewModelScope.launch {
            if (!networkMonitor.isConnected.value) {
                _uiState.value = TransactionUiState.Error("Нет соединения с интернетом")
                return@launch
            }
            _uiState.value = TransactionUiState.Loading
            try {
                val spendingList = retryIO(times = 3, delayMillis = 2000) {
                    repository.getTransactionsByPeriod(
                        accountId, startDate, endDate
                    ).filter {
                        it.category.isIncome == isIncome
                    }
                }
                val total = spendingList.sumOf { it.amount.toDouble() }
                _uiState.value = TransactionUiState.Success(spendingList, "$total")
            } catch (e: IOException) {
                _uiState.value = TransactionUiState.Error("Нет соединения с интернетом.")
            } catch (e: HttpException) {
                val code = e.code()
                val errorBody = e.response()?.errorBody()?.string()
                _uiState.value = TransactionUiState.Error("Ошибка сервера ($code): $errorBody")
            } catch (e: Exception) {
                _uiState.value = TransactionUiState.Error("Неизвестная ошибка: ${e.localizedMessage}")
            }
        }
    }

    private fun handleItemClick() {
        //TODO
    }


}


