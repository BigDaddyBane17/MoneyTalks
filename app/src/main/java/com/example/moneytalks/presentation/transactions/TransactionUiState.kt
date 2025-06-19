package com.example.moneytalks.presentation.transactions

import com.example.moneytalks.data.remote.model.TransactionResponse

sealed class TransactionUiState {
    object Loading: TransactionUiState()
    data class Success(val items: List<TransactionResponse>, val total: String): TransactionUiState()
    data class Error(val message: String): TransactionUiState()
}