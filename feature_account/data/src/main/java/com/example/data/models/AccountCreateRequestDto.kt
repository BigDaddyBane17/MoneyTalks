package com.example.data.models

import kotlinx.serialization.Serializable

@Serializable
data class AccountCreateRequestDto(
    val name: String,
    val balance: String,
    val currency: String
)
