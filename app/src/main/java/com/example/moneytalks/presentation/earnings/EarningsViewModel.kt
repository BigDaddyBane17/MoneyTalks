package com.example.moneytalks.presentation.earnings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneytalks.domain.model.Income
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EarningsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<EarningsUiState>(EarningsUiState.Loading)
    val uiState: StateFlow<EarningsUiState> = _uiState.asStateFlow()

    fun handleIntent(intent: EarningsIntent) {
        when (intent) {
            EarningsIntent.LoadEarnings -> loadEarnings()
            EarningsIntent.OnItemClicked -> handleItemClick()
            EarningsIntent.AddEarning -> addEarning()
            EarningsIntent.GoToHistory -> goToHistory()
        }

    }

    private fun handleItemClick() {

    }

    private fun addEarning() {

    }

    private fun goToHistory() {

    }

    private fun loadEarnings() {
        _uiState.value = EarningsUiState.Loading
        viewModelScope.launch {
            delay(300)

            val earnings = listOf(
                Income(
                    id = 1,
                    title = "Зарплата",
                    amount = "500 000",
                    currency = "₽"
                ),
                Income(
                    id = 2,
                    title = "Подработка",
                    amount = "100 000",
                    currency = "₽"
                )
            )

            _uiState.value = EarningsUiState.Success(earnings)
        }
    }


}