package com.example.moneytalks.features.account.presentation.account

import com.example.moneytalks.features.account.domain.model.Account


sealed class AccountUiState {
    object Loading : AccountUiState()
    data class Success(
        val account: Account?
    ) : AccountUiState()
    data class Error(val message: String) : AccountUiState()
}
