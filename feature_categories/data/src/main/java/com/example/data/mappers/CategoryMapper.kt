package com.example.data.mappers

import com.example.core.data.entities.CategoryEntity
import com.example.core.data.models.CategoryDto
import com.example.core.domain.models.Category

fun CategoryDto.toDomain(): Category =
    Category(
        id = this.id,
        name = this.name,
        emoji = this.emoji,
        isIncome = this.isIncome
    )

fun Category.toDto(): CategoryDto =
    CategoryDto(
        id = this.id,
        name = this.name,
        emoji = this.emoji,
        isIncome = this.isIncome
    )


fun CategoryEntity.toDomain(): Category =
    Category(
        id = this.id,
        name = this.name,
        emoji = this.emoji,
        isIncome = this.isIncome
    )

fun Category.toEntity(): CategoryEntity =
    CategoryEntity(
        id = this.id,
        name = this.name,
        emoji = this.emoji,
        isIncome = this.isIncome
    )

fun CategoryDto.toEntity(): CategoryEntity =
    CategoryEntity(
        id = this.id,
        name = this.name,
        emoji = this.emoji,
        isIncome = this.isIncome
    )

fun CategoryEntity.toDto(): CategoryDto =
    CategoryDto(
        id = this.id,
        name = this.name,
        emoji = this.emoji,
        isIncome = this.isIncome
    )