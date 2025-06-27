package com.example.moneytalks.features.transaction.presentation.create_spending_transaction

import com.example.moneytalks.features.account.data.remote.model.AccountDto
import com.example.moneytalks.features.categories.data.remote.model.CategoryDto

sealed class CreateSpendingTransactionUiState {
    object Loading: CreateSpendingTransactionUiState()
    data class Data(
        val accounts: List<AccountDto>,
        val categories: List<CategoryDto>
    ) : CreateSpendingTransactionUiState()
    object Success                       : CreateSpendingTransactionUiState()
    data class Error(val message: String): CreateSpendingTransactionUiState()
}