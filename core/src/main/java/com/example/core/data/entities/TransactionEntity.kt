package com.example.core.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey val id: Int, // <0 — локальный, >0 — серверный
    val accountId: Int,
    val accountName: String,
    val categoryId: Int,
    val categoryName: String,
    val categoryEmoji: String,
    val isIncome: Boolean,
    val amount: String,
    val transactionDate: String,
    val comment: String?,
    val isSynced: Boolean = false,
    val isDeleted: Boolean = false,
    val lastModified: Long = System.currentTimeMillis(),
    val serverVersion: Long? = null
)
