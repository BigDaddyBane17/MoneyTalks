package com.example.data.models
import kotlinx.serialization.Serializable

@Serializable
data class AccountHistoryResponseDto(
    val accountId: Int,
    val accountName: String,
    val currency: String,
    val currentBalance: String,
    val history: List<AccountHistoryDto>
)
