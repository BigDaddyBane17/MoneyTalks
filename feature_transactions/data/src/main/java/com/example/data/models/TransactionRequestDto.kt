package com.example.data.models

import kotlinx.serialization.Serializable

@Serializable
data class TransactionRequestDto(
    val accountId: Int,
    val categoryId: Int,
    val amount: String,
    val transactionDate: String,
    val comment: String?
)
