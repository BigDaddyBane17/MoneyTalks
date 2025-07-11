package com.example.data.mappers

import com.example.core.data.models.CategoryDto
import com.example.core.domain.models.Category
import javax.inject.Inject

class CategoryMapper @Inject constructor() {
    
    fun toDomain(dto: CategoryDto): Category {
        return Category(
            id = dto.id,
            name = dto.name,
            emoji = dto.emoji,
            isIncome = dto.isIncome
        )
    }
    
    fun toDto(domain: Category): CategoryDto {
        return CategoryDto(
            id = domain.id,
            name = domain.name,
            emoji = domain.emoji,
            isIncome = domain.isIncome
        )
    }
} 