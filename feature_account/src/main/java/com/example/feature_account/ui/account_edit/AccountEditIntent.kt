package com.example.feature_account.ui.account_edit

sealed interface AccountEditIntent {
    data object LoadAccount : AccountEditIntent
    data class NameChanged(val name: String) : AccountEditIntent
    data class BalanceChanged(val balance: String) : AccountEditIntent
    data object SaveAccount : AccountEditIntent
    data object ClearError : AccountEditIntent
}