package com.example.core.utils

fun String.toCurrencySymbol(): String {
    return when (this.uppercase()) {
        "RUB", "RUR" -> "₽"
        "USD" -> "$"
        "EUR" -> "€"
        else -> this
    }
} 