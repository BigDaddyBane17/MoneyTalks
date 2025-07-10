package com.example.domain.models

import java.time.LocalDateTime

data class Transaction(
    val id: Int,
    val accountName: String,
    val categoryName: String,
    val categoryEmoji: String,
    val amount: String,
    val transactionDate: LocalDateTime,
    val comment: String?
) 