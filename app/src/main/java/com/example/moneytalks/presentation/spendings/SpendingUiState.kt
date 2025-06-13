package com.example.moneytalks.presentation.spendings

import com.example.moneytalks.domain.model.Expenses

sealed class SpendingUiState {
    object Loading: SpendingUiState()
    data class Success(val items: List<Expenses>, val total: String): SpendingUiState()
    data class Error(val message: String): SpendingUiState()
}