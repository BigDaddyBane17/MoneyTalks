package com.example.moneytalks.features.transaction.presentation.transactions

import com.example.moneytalks.features.transaction.data.remote.model.TransactionResponseDto

sealed class TransactionUiState {
    object Loading: TransactionUiState()
    data class Success(val items: List<TransactionResponseDto>, val total: String): TransactionUiState()
    data class Error(val message: String): TransactionUiState()
}
