package com.example.feature_account.ui.account_main

sealed interface AccountIntent {
    data object LoadAccounts : AccountIntent
    data class SelectAccount(val accountId: Int) : AccountIntent
    data class CurrencyClick(val currencyCode: String) : AccountIntent
    data object Refresh : AccountIntent
}