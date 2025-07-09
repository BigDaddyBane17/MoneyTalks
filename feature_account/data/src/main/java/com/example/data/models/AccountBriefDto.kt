package com.example.data.models

import kotlinx.serialization.Serializable

@Serializable
data class AccountBriefDto(
    val id: Int,
    val name: String,
    val balance: String,
    val currency: String
)
