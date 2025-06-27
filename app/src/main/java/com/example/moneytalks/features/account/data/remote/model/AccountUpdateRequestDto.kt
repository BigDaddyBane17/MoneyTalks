package com.example.moneytalks.features.account.data.remote.model
import kotlinx.serialization.Serializable

@Serializable
data class AccountUpdateRequestDto(
    val name: String,
    val balance: String,
    val currency: String
)
