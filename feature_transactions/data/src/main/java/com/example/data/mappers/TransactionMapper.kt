package com.example.data.mappers

import com.example.core.data.entities.TransactionEntity
import com.example.data.models.TransactionRequestDto
import com.example.data.models.TransactionResponseDto
import com.example.domain.models.Transaction
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.*

fun String.parseDateTime(): LocalDateTime {
    return try {
        LocalDateTime.parse(this, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    } catch (e: Exception) {
        try {
            ZonedDateTime.parse(this, DateTimeFormatter.ISO_DATE_TIME).toLocalDateTime()
        } catch (e2: Exception) {
            try {
                val instant = Instant.parse(this)
                LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
            } catch (e3: Exception) {
                LocalDateTime.parse(this.replace(" ", "T"))
            }
        }
    }
}

fun TransactionResponseDto.toEntity(): TransactionEntity =
    TransactionEntity(
        id = this.id,
        accountId = this.account.id,
        accountName = this.account.name,
        categoryId = this.category.id,
        categoryName = this.category.name,
        categoryEmoji = this.category.emoji,
        isIncome = this.category.isIncome,
        amount = this.amount,
        transactionDate = this.transactionDate,
        comment = this.comment,
        isSynced = true,
        isDeleted = false,
        lastModified = this.updatedAt.parseDateTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
        serverVersion = this.updatedAt.parseDateTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    )

fun TransactionEntity.toDomain(): Transaction = Transaction(
    id = id,
    accountName = accountName,
    categoryName = categoryName,
    categoryEmoji = categoryEmoji,
    amount = amount,
    transactionDate = transactionDate.parseDateTime(),
    comment = comment,
    isIncome = isIncome
)

fun TransactionEntity.toRequestDto(): TransactionRequestDto =
    TransactionRequestDto(
        accountId = accountId,
        categoryId = categoryId,
        amount = amount,
        transactionDate = transactionDate,
        comment = comment
    )

fun TransactionResponseDto.toDomain(): Transaction =
    Transaction(
        id = id,
        accountName = account.name,
        categoryName = category.name,
        categoryEmoji = category.emoji,
        amount = amount,
        transactionDate = transactionDate.parseDateTime(),
        comment = comment,
        isIncome = category.isIncome
    )
