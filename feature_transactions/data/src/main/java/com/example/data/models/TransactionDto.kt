package com.example.data.models
import kotlinx.serialization.Serializable

@Serializable
data class TransactionDto(
    val id: Int,
    val accountId: Int,
    val categoryId: Int,
    val amount: String,
    val transactionDate: String,
    val comment: String?,
    val createdAt: String,
    val updatedAt: String
)
