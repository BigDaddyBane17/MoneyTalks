package com.example.moneytalks.features.account.data.model

import kotlinx.serialization.Serializable

@Serializable
data class AccountDto(
    val id: Int,
    val userId: String,
    val name: String,
    val balance: String,
    val currency: String,
    val createdAt: String,
    val updatedAt: String,
)
