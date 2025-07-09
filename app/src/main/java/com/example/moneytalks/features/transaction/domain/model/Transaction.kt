package com.example.moneytalks.features.transaction.domain.model

import com.example.moneytalks.features.categories.domain.model.Category
import java.math.BigDecimal
import java.time.LocalDateTime

data class Transaction(
    val id: Int,
    val accountId: Int,
    val category: Category,
    val amount: BigDecimal,
    val transactionDate: LocalDateTime,
    val comment: String?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)

