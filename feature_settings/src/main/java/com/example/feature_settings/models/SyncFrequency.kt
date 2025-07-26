package com.example.feature_settings.models

enum class SyncFrequency(val displayName: String, val hours: Int) {
    MANUAL("Вручную", 0),
    HOURLY("Каждый час", 1),
    DAILY("Ежедневно", 24),
    WEEKLY("Еженедельно", 168)
} 