package com.example.moneytalks.features.account.data.remote.model
import kotlinx.serialization.Serializable

@Serializable
data class StatItemDto(
    val categoryId: Int,
    val categoryName: String,
    val emoji: String,
    val amount: String
)
