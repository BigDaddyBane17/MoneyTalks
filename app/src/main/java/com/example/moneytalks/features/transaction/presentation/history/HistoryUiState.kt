package com.example.moneytalks.features.transaction.presentation.history

import com.example.moneytalks.features.transaction.data.remote.model.TransactionResponseDto

sealed class HistoryUiState {
    object Loading: HistoryUiState()
    data class Success(
        val items: List<TransactionResponseDto>,
        val total: String
    ): HistoryUiState()
    data class Error(val message: String): HistoryUiState()
}
