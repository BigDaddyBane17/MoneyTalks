package com.example.moneytalks.presentation.spendings

import com.example.moneytalks.data.remote.model.TransactionResponse
import com.example.moneytalks.domain.model.Expenses

sealed class SpendingUiState {
    object Loading: SpendingUiState()
    data class Success(val items: List<TransactionResponse>, val total: String): SpendingUiState()
    data class Error(val message: String): SpendingUiState()
}