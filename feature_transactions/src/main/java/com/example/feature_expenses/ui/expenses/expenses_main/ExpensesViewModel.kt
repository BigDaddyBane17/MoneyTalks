package com.example.feature_expenses.ui.expenses.expenses_main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetTodayExpensesUseCase
import com.example.domain.usecase.GetFirstAccountUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

class ExpensesViewModel @Inject constructor(
    private val getTodayExpensesUseCase: GetTodayExpensesUseCase,
    private val getFirstAccountUseCase: GetFirstAccountUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ExpensesState())
    val state: StateFlow<ExpensesState> = _state.asStateFlow()

    init {
        handleIntent(ExpensesIntent.LoadTodayExpenses)
    }

    fun handleIntent(intent: ExpensesIntent) {
        when (intent) {
            is ExpensesIntent.LoadTodayExpenses -> loadTodayExpenses()
            is ExpensesIntent.Refresh -> loadTodayExpenses()
        }
    }

    private fun loadTodayExpenses() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            
            try {
                // Получаем первый счет
                val firstAccount = getFirstAccountUseCase()
                
                if (firstAccount == null) {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = "Нет доступных счетов"
                    )
                    return@launch
                }
                
                // Обновляем состояние с информацией о счете
                _state.value = _state.value.copy(
                    accountId = firstAccount.id,
                    currency = firstAccount.currency
                )
                
                // Загружаем расходы для этого счета
                getTodayExpensesUseCase(firstAccount.id)
                    .catch { error ->
                        _state.value = _state.value.copy(
                            isLoading = false,
                            error = error.message ?: "Произошла ошибка"
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
                    error = e.message ?: "Произошла ошибка при загрузке счета"
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