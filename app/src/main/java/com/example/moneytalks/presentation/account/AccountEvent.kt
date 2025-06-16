package com.example.moneytalks.presentation.account

sealed class AccountNavigationEvent {
    object NavigateToBalance: AccountNavigationEvent()
}