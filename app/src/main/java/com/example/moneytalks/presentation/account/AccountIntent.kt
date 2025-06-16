package com.example.moneytalks.presentation.account

sealed class AccountIntent {
    object LoadAccountData: AccountIntent()
    object BalanceClick: AccountIntent()
    data class CurrencyClick(val currency: String): AccountIntent()
    //object CreateAccount: AccountIntent()
}