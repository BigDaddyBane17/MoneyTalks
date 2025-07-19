package com.example.core.network

import com.example.core.data.entities.TransactionEntity

fun TransactionDto.toEntity(
    accountName: String,
    categoryName: String,
    categoryEmoji: String,
    isIncome: Boolean
): TransactionEntity {
    return TransactionEntity(
        id = id,
        accountId = accountId,
        accountName = accountName,
        categoryId = categoryId,
        categoryName = categoryName,
        categoryEmoji = categoryEmoji,
        isIncome = isIncome,
        amount = amount,
        transactionDate = transactionDate,
        comment = comment,
        isSynced = true,
        isDeleted = false
    )
}

fun TransactionResponseDto.toEntity(): TransactionEntity {
    return TransactionEntity(
        id = id,
        accountId = accountId,
        accountName = "",
        categoryId = categoryId,
        categoryName = "",
        categoryEmoji = "",
        isIncome = false,
        amount = amount,
        transactionDate = transactionDate,
        comment = comment,
        isSynced = true,
        isDeleted = false
    )
} 