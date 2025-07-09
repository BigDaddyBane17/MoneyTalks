package com.example.moneytalks.features.transaction.data.mappers

import com.example.moneytalks.features.categories.data.mappers.toDomain
import com.example.moneytalks.features.categories.domain.model.Category
import com.example.moneytalks.features.transaction.data.remote.model.TransactionDto
import com.example.moneytalks.features.transaction.data.remote.model.TransactionResponseDto
import com.example.moneytalks.features.transaction.domain.model.Transaction
import java.time.LocalDateTime

fun TransactionDto.toDomain(category: Category): Transaction = Transaction(
    id = id,
    accountId = accountId,
    category = category,
    amount = amount.toBigDecimal(),
    transactionDate = LocalDateTime.parse(transactionDate), // если формат iso
    comment = comment,
    createdAt = LocalDateTime.parse(createdAt),
    updatedAt = LocalDateTime.parse(updatedAt)
)

fun TransactionResponseDto.toDomain(): Transaction = Transaction(
    id = id,
    accountId = account.id,
    category = category.toDomain(),
    amount = amount.toBigDecimal(),
    transactionDate = LocalDateTime.parse(transactionDate),
    comment = comment,
    createdAt = LocalDateTime.parse(createdAt),
    updatedAt = LocalDateTime.parse(updatedAt)
)