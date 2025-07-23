package com.example.analytics.donutChart

import androidx.compose.ui.graphics.Color

fun generateColorFromLabel(label: String): Color {
    val hash = label.hashCode()
    val r = (hash shr 16 and 0xFF)
    val g = (hash shr 8 and 0xFF)
    val b = (hash and 0xFF)
    return Color(r, g, b)
}

data class ChartSlice(
    val value: Float,
    val label: String,
    val color: Color? = null,
    val icon: String? = null
)
