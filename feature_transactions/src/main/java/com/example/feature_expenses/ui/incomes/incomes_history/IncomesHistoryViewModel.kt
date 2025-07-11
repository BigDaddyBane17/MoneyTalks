package com.example.feature_expenses.ui.incomes.incomes_history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.usecase.GetCurrentAccountUseCase
import com.example.domain.usecase.GetHistoryIncomesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.time.LocalDate
import javax.inject.Inject

class IncomesHistoryViewModel @Inject constructor(
    private val getHistoryIncomesUseCase: GetHistoryIncomesUseCase,
    private val getCurrentAccountUseCase: GetCurrentAccountUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(IncomesHistoryState())
    val state: StateFlow<IncomesHistoryState> = _state.asStateFlow()

    private val _startDate = MutableStateFlow(LocalDate.now().withDayOfMonth(1))
    private val _endDate = MutableStateFlow(LocalDate.now())

    init {
        observeAccountAndDates()
    }

    fun handleIntent(intent: IncomesHistoryIntent) {
        when (intent) {
            is IncomesHistoryIntent.LoadHistory -> {
                _startDate.value = intent.startDate
                _endDate.value = intent.endDate
                // Данные загрузятся автоматически через observeAccountAndDates
            }
            is IncomesHistoryIntent.Refresh -> {
                // Принудительно обновляем данные о текущем счете
                refreshCurrentAccount()
            }
        }
    }

    private fun observeAccountAndDates() {
        viewModelScope.launch {
            combine(
                getCurrentAccountUseCase(),
                _startDate,
                _endDate
            ) { account, startDate, endDate ->
                Triple(account, startDate, endDate)
            }.collect { (account, startDate, endDate) ->
                if (account != null) {
                    _state.value = _state.value.copy(
                        accountId = account.id,
                        currency = account.currency
                    )
                    loadHistory(account.id, startDate, endDate)
                } else {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = "Нет доступных счетов",
                        accountId = null
                    )
                }
            }
        }
    }

    private fun refreshCurrentAccount() {
        viewModelScope.launch {
            try {
                val currentAccount = getCurrentAccountUseCase.getCurrentAccount()
                if (currentAccount != null) {
                    _state.value = _state.value.copy(
                        accountId = currentAccount.id,
                        currency = currentAccount.currency
                    )
                    loadHistory(currentAccount.id, _startDate.value, _endDate.value)
                } else {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = "Нет доступных счетов",
                        accountId = null
                    )
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Ошибка обновления данных счета"
                )
            }
        }
    }

    private fun loadHistoryForCurrentState() {
        val currentState = _state.value
        if (currentState.accountId != null) {
            loadHistory(currentState.accountId, _startDate.value, _endDate.value)
        }
    }

    private fun loadHistory(accountId: Int, startDate: LocalDate, endDate: LocalDate) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            
            try {
                getHistoryIncomesUseCase(accountId, startDate, endDate)
                    .catch { exception ->
                        _state.value = _state.value.copy(
                            isLoading = false,
                            error = exception.message ?: "Ошибка загрузки истории доходов"
                        )
                    }
                    .collect { transactions ->
                        val total = calculateTotal(transactions.map { it.amount })
                        _state.value = _state.value.copy(
                            isLoading = false,
                            incomes = transactions,
                            totalAmount = total,
                            error = null
                        )
                    }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Ошибка загрузки истории доходов"
                )
            }
        }
    }

    private fun calculateTotal(amounts: List<String>): String {
        return try {
            amounts.sumOf { BigDecimal(it) }.toString()
        } catch (e: Exception) {
            "0.00"
        }
    }
}