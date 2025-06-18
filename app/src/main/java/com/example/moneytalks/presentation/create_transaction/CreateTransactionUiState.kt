package com.example.moneytalks.presentation.create_transaction

import com.example.moneytalks.data.remote.model.Account
import com.example.moneytalks.data.remote.model.Category

sealed class CreateTransactionUiState {
    object Loading                       : CreateTransactionUiState()
    data class Data(
        val accounts: List<Account>,
        val categories: List<Category>
    ) : CreateTransactionUiState()
    object Success                       : CreateTransactionUiState()
    data class Error(val message: String): CreateTransactionUiState()
}