package com.example.moneytalks.features.categories.data.mappers

import com.example.moneytalks.features.account.data.model.StatItemDto
import com.example.moneytalks.features.account.domain.model.StatItem
import com.example.moneytalks.features.categories.data.remote.model.CategoryDto
import com.example.moneytalks.features.categories.domain.model.Category


fun StatItemDto.toDomain(): StatItem = StatItem(
    categoryId = categoryId,
    categoryName = categoryName,
    emoji = emoji,
    amount = amount
)

fun CategoryDto.toDomain(): Category = Category(
    id = id,
    name = name,
    emoji = emoji,
    isIncome = isIncome
)
