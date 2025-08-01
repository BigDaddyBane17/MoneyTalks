package com.example.feature_expenses.ui.expenses.expenses_main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.models.Account
import com.example.feature_expenses.usecase.GetTodayExpensesUseCase
import com.example.core.usecase.GetCurrentAccountUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

class ExpensesViewModel @Inject constructor(
    private val getTodayExpensesUseCase: GetTodayExpensesUseCase,
    private val getCurrentAccountUseCase: GetCurrentAccountUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ExpensesState())
    val state: StateFlow<ExpensesState> = _state.asStateFlow()

    init {

        observeSelectedAccount()
    }

    fun handleIntent(intent: ExpensesIntent) {
        when (intent) {
            is ExpensesIntent.LoadTodayExpenses -> loadTodayExpenses()
            is ExpensesIntent.Refresh -> loadTodayExpenses()
        }
    }

    private fun observeSelectedAccount() {
        viewModelScope.launch {
            getCurrentAccountUseCase()
                .filterNotNull()
                .distinctUntilChanged()
                .collect { account ->
                    loadTodayExpenses(account)
                }
        }
    }

    private fun loadTodayExpenses() {
        viewModelScope.launch {
            try {
                val currentAccount = getCurrentAccountUseCase.getCurrentAccount()
                if (currentAccount != null) {
                    loadTodayExpenses(currentAccount)
                } else {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = "Нет доступных счетов. Проверьте подключение к интернету."
                    )
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = "Ошибка загрузки данных: ${e.message ?: "Проверьте подключение к интернету"}"
                )
            }
        }
    }

    private fun loadTodayExpenses(account: Account) {
        viewModelScope.launch {
            // Показываем лоадер только если данных ещё нет
            if (_state.value.expenses.isEmpty()) {
                _state.value = _state.value.copy(isLoading = true, error = null)
            } else {
                _state.value = _state.value.copy(isLoading = false, error = null)
            }
            try {
                _state.value = _state.value.copy(
                    accountId = account.id,
                    currency = account.currency
                )
                getTodayExpensesUseCase(account.id)
                    .catch { error ->
                        _state.value = _state.value.copy(
                            isLoading = false,
                            error = "Ошибка загрузки расходов: ${error.message ?: "Проверьте подключение к интернету"}"
                        )
                    }
                    .collect { expenses ->
                        val totalAmount = calculateTotalAmount(expenses.map { it.amount })
                        _state.value = _state.value.copy(
                            isLoading = false,
                            expenses = expenses,
                            totalAmount = totalAmount,
                            error = null
                        )
                    }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = "Ошибка загрузки расходов: ${e.message ?: "Проверьте подключение к интернету"}"
                )
            }
        }
    }

    private fun calculateTotalAmount(amounts: List<String>): String {
        return try {
            amounts.sumOf { BigDecimal(it) }.toString()
        } catch (e: Exception) {
            "0.00"
        }
    }
}