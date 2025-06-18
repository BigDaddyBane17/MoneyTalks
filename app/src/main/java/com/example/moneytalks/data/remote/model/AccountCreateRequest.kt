package com.example.moneytalks.data.remote.model

data class AccountCreateRequest(
    val name: String,
    val balance: String,
    val currency: String
)
