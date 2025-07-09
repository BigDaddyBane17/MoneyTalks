package com.example.moneytalks.features.account.presentation.account

import com.example.moneytalks.features.account.domain.model.AccountBrief

sealed class AccountIntent {
    data class CurrencyClick(val accountId: Int, val currency: String) : AccountIntent()
    data class AccountEdit(val account: AccountBrief) : AccountIntent()
    data class SelectAccount(val accountId: Int) : AccountIntent()
}