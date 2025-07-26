package com.example.analytics.donutChart

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance

fun generateColorFromLabel(label: String): Color {
    val hash = label.hashCode()
    val r = (hash shr 16 and 0xFF)
    val g = (hash shr 8 and 0xFF)
    val b = (hash and 0xFF)
    return Color(r, g, b)
}


fun prepareSlices(data: List<ChartSlice>, maxVisible: Int = 6, minPercent: Float = 5f): List<ChartSlice> {
    val total = data.sumOf { it.value }
    val sorted = data.sortedByDescending { it.value }
    val visible = sorted.filter { it.value / total * 100 >= minPercent }
    val others = sorted.filterNot { it in visible }
    val combined = if (others.isNotEmpty()) {
        visible.take(maxVisible - 1) + ChartSlice(
            value = others.sumOf { it.value },
            label = "Другое",
            icon = "📦"
        )
    } else {
        visible
    }
    return combined
}

data class ChartSlice(
    val value: Double,
    val label: String,
    val color: Color? = null,
    val icon: String? = null
)
