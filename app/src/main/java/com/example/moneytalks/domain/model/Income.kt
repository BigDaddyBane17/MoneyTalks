package com.example.moneytalks.domain.model

data class Income(
    val id: Int,
    val title: String,
    val description: String? = null,
    val amount: String,
    val currency: String,
)
