package com.example.feature_expenses.ui.incomes.incomes_main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.models.Account
import com.example.feature_expenses.usecase.GetTodayIncomesUseCase
import com.example.core.usecase.GetCurrentAccountUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

class IncomesViewModel @Inject constructor(
    private val getTodayIncomesUseCase: GetTodayIncomesUseCase,
    private val getCurrentAccountUseCase: GetCurrentAccountUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(IncomesState())
    val state: StateFlow<IncomesState> = _state.asStateFlow()

    init {
        observeSelectedAccount()
    }

    fun handleIntent(intent: IncomesIntent) {
        when (intent) {
            is IncomesIntent.LoadTodayIncomes -> loadTodayIncomes()
            is IncomesIntent.Refresh -> loadTodayIncomes()
        }
    }

    private fun observeSelectedAccount() {
        viewModelScope.launch {
            getCurrentAccountUseCase().collect { account ->
                if (account != null) {
                    loadTodayIncomes(account)
                } else {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = "Нет доступных счетов"
                    )
                }
            }
        }
    }

    private fun loadTodayIncomes() {
        viewModelScope.launch {
            try {
                val currentAccount = getCurrentAccountUseCase.getCurrentAccount()
                if (currentAccount != null) {
                    loadTodayIncomes(currentAccount)
                } else {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = "Нет доступных счетов"
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

    private fun loadTodayIncomes(account: Account) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            
            try {
                _state.value = _state.value.copy(
                    accountId = account.id,
                    currency = account.currency
                )
                getTodayIncomesUseCase(account.id)
                    .catch { error ->
                        _state.value = _state.value.copy(
                            isLoading = false,
                            error = error.message ?: "Произошла ошибка"
                        )
                    }
                    .collect { incomes ->
                        val totalAmount = calculateTotalAmount(incomes.map { it.amount })
                        _state.value = _state.value.copy(
                            isLoading = false,
                            incomes = incomes,
                            totalAmount = totalAmount,
                            error = null
                        )
                    }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Произошла ошибка при загрузке доходов"
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