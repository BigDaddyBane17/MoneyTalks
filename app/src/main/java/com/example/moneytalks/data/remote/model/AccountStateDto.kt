package com.example.moneytalks.data.remote.model
import kotlinx.serialization.Serializable

@Serializable
data class AccountStateDto(
    val id: Int,
    val name: String,
    val balance: String,
    val currency: String
)