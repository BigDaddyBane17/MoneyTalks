package com.example.moneytalks.presentation.account

sealed class AccountIntent {
    object LoadAccountData: AccountIntent()
    object BalanceClick: AccountIntent()
    object CurrencyClick: AccountIntent()
    object CreateAccount: AccountIntent()
}