package com.example.moneytalks.features.transaction.domain.model

data class Transaction(
    val id: Int,
    val leadIcon: String,
    val title: String,
    val description: String? = null,
    val amount: String,
    val currency: String,

)