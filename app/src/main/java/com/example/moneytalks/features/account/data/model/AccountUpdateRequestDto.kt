package com.example.moneytalks.features.account.data.model
import kotlinx.serialization.Serializable

@Serializable
data class AccountUpdateRequestDto(
    val name: String,
    val balance: String,
    val currency: String
)
