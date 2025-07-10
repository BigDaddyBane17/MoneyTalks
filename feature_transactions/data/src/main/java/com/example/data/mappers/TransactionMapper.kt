package com.example.data.mappers

import com.example.data.models.TransactionResponseDto
import com.example.domain.models.Transaction
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class TransactionMapper @Inject constructor() {
    
    fun toDomain(dto: TransactionResponseDto): Transaction {
        return Transaction(
            id = dto.id,
            accountName = dto.account.name,
            categoryName = dto.category.name,
            categoryEmoji = dto.category.emoji,
            amount = dto.amount,
            transactionDate = parseDateTime(dto.transactionDate),
            comment = dto.comment
        )
    }
    
    private fun parseDateTime(dateString: String): LocalDateTime {
        return try {
            // Сначала пробуем парсить как ISO_LOCAL_DATE_TIME (без часового пояса)
            LocalDateTime.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        } catch (e: Exception) {
            try {
                // Если не получилось, пробуем как ISO_DATE_TIME с часовым поясом
                val zonedDateTime = java.time.ZonedDateTime.parse(dateString, DateTimeFormatter.ISO_DATE_TIME)
                // Конвертируем в локальное время
                zonedDateTime.toLocalDateTime()
            } catch (e2: Exception) {
                try {
                    // Еще один вариант - ISO_INSTANT (2025-07-10T10:52:11.058Z)
                    val instant = java.time.Instant.parse(dateString)
                    // Конвертируем в локальное время используя системный часовой пояс
                    LocalDateTime.ofInstant(instant, java.time.ZoneId.systemDefault())
                } catch (e3: Exception) {
                    // Последняя попытка - заменяем пробелы на T
                    LocalDateTime.parse(dateString.replace(" ", "T"))
                }
            }
        }
    }
} 