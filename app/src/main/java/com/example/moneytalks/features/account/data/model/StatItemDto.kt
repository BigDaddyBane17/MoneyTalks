package com.example.moneytalks.features.account.data.model
import kotlinx.serialization.Serializable

@Serializable
data class StatItemDto(
    val categoryId: Int,
    val categoryName: String,
    val emoji: String,
    val amount: String
)
