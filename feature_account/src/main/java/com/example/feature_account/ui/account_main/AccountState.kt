package com.example.feature_account.ui.account_main

import com.example.core.domain.models.Account

sealed interface AccountUiState {
    data object Loading : AccountUiState
    data class Success(
        val account: Account?,
        val accounts: List<Account> = emptyList()
    ) : AccountUiState
    data class Error(val message: String) : AccountUiState
}