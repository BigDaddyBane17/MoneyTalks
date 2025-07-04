package com.example.moneytalks.features.categories.domain.model

data class Category(
    val id: Int,
    val emoji: String,
    val name: String,
    val isIncome: Boolean
)