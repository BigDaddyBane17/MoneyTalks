package com.example.moneytalks.features.account.domain.model

data class AccountState(
    val id: Int,
    val name: String,
    val balance: String,
    val currency: String
)