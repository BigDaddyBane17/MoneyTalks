package com.example.core_ui.theme

import androidx.compose.ui.graphics.Color


object ThemeProvider {
    val availableThemes = listOf(
        AppTheme(
            id = "default",
            name = "По умолчанию",
            primaryColor = Color(0xFF2196F3),
            secondaryColor = Color(0xFF1976D2),
            accentColor = Color(0xFF03DAC6)
        ),
        AppTheme(
            id = "green",
            name = "Зелёная",
            primaryColor = Color(0xFF4CAF50),
            secondaryColor = Color(0xFF388E3C),
            accentColor = Color(0xFF8BC34A)
        ),
        AppTheme(
            id = "purple",
            name = "Фиолетовая",
            primaryColor = Color(0xFF9C27B0),
            secondaryColor = Color(0xFF7B1FA2),
            accentColor = Color(0xFFE1BEE7)
        ),
        AppTheme(
            id = "orange",
            name = "Оранжевая",
            primaryColor = Color(0xFFFF9800),
            secondaryColor = Color(0xFFF57C00),
            accentColor = Color(0xFFFFB74D)
        ),
        AppTheme(
            id = "red",
            name = "Красная",
            primaryColor = Color(0xFFF44336),
            secondaryColor = Color(0xFFD32F2F),
            accentColor = Color(0xFFFFCDD2)
        )
    )

    fun getThemeById(id: String): AppTheme {
        return availableThemes.find { it.id == id } ?: availableThemes.first()
    }
} 