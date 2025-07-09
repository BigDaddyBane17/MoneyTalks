package com.example.moneytalks.features.account.domain.model

data class Account(
    val id: Int,
    val userId: String?,
    val name: String,
    val balance: String,
    val currency: String,
    val createdAt: String,
    val updatedAt: String
)