package com.example.moneytalks.features.transaction.presentation.create_earning_transaction

import com.example.moneytalks.features.account.data.remote.model.AccountDto
import com.example.moneytalks.features.categories.data.remote.model.CategoryDto

sealed class CreateEarningTransactionUiState {
    object Loading: CreateEarningTransactionUiState()
    data class Data(
        val accounts: List<AccountDto>,
        val categories: List<CategoryDto>
    ) : CreateEarningTransactionUiState()
    object Success                       : CreateEarningTransactionUiState()
    data class Error(val message: String): CreateEarningTransactionUiState()
}