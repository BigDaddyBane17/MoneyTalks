package com.example.moneytalks.presentation.transactions

import com.example.moneytalks.data.remote.model.TransactionResponseDto

sealed class TransactionUiState {
    object Loading: TransactionUiState()
    data class Success(val items: List<TransactionResponseDto>, val total: String): TransactionUiState()
    data class Error(val message: String): TransactionUiState()
}