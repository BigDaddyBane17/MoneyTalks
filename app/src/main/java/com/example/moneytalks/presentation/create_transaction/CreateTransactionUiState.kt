package com.example.moneytalks.presentation.create_transaction

import com.example.moneytalks.data.remote.model.AccountDto
import com.example.moneytalks.data.remote.model.CategoryDto

sealed class CreateTransactionUiState {
    object Loading: CreateTransactionUiState()
    data class Data(
        val accounts: List<AccountDto>,
        val categories: List<CategoryDto>
    ) : CreateTransactionUiState()
    object Success                       : CreateTransactionUiState()
    data class Error(val message: String): CreateTransactionUiState()
}