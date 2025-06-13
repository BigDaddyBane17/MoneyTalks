package com.example.moneytalks.presentation.account

import com.example.moneytalks.domain.model.Account

sealed class AccountUiState {
    object Loading : AccountUiState()
    data class Success(
        val account: Account
    ) : AccountUiState()
    data class Error(val message: String) : AccountUiState()
}