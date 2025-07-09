package com.example.moneytalks.features.transaction.data.remote.model
import com.example.moneytalks.features.account.data.model.AccountBriefDto
import com.example.moneytalks.features.categories.data.remote.model.CategoryDto
import kotlinx.serialization.Serializable

@Serializable
data class TransactionResponseDto(
    val id: Int,
    val account: AccountBriefDto,
    val category: CategoryDto,
    val amount: String,
    val transactionDate: String,
    val comment: String?,
    val createdAt: String,
    val updatedAt: String
)
