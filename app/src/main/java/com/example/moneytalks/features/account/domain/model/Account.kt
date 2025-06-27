package com.example.moneytalks.features.account.domain.model

data class Account(
    val id: Int,
    val amount: String,
    val currency: String,
)