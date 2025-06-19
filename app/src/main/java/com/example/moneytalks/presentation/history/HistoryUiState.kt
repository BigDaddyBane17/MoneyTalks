package com.example.moneytalks.presentation.history

import com.example.moneytalks.data.remote.model.TransactionResponse

sealed class HistoryUiState {
    object Loading: HistoryUiState()
    data class Success(
        val items: List<TransactionResponse>,
        val total: String
    ): HistoryUiState()
    data class Error(val message: String): HistoryUiState()
}