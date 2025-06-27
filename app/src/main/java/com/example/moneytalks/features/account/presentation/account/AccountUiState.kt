package com.example.moneytalks.features.account.presentation.account

import com.example.moneytalks.features.account.data.remote.model.AccountDto

sealed class AccountUiState {
    object Loading : AccountUiState()
    data class Success(
        val account: AccountDto?
    ) : AccountUiState()
    data class Error(val message: String) : AccountUiState()
}