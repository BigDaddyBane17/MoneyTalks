package com.example.moneytalks.features.account.domain.model

data class Account(
    val id: Int,
    val name: String,
    val balance: String,
    val currency: String,
)