package com.example.moneytalks.domain.model

data class Expenses(
    val id: Int,
    val leadIcon: String,
    val title: String,
    val description: String? = null,
    val amount: String,
    val currency: String,

)