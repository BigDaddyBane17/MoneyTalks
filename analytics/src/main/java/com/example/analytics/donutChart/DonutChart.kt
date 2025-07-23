package com.example.analytics.donutChart

import androidx.compose.foundation.Canvas
import android.graphics.Paint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun DonutChart(
    slices: List<ChartSlice>,
    modifier: Modifier = Modifier,
    strokeWidth: Float = 40f
) {
    val total = slices.sumOf { it.value.toDouble() }
    if (total == 0.0) return

    val sweepAngles = remember(slices) { slices.map { (it.value / total) * 360f } }

    Canvas(modifier = modifier.fillMaxSize()) {
        val radius = size.minDimension / 2
        val center = Offset(size.width / 2, size.height / 2)
        var startAngle = -90f

        slices.forEachIndexed { index, slice ->
            val sweepAngle = sweepAngles[index]
            var color = slice.color ?: generateColorFromLabel(
                slice.label
            )

            drawArc(
                color = color,
                startAngle = startAngle,
                sweepAngle = sweepAngle.toFloat(),
                useCenter = false,
                topLeft = Offset(center.x - radius, center.y - radius),
                size = Size(radius * 2, radius * 2),
                style = Stroke(width = strokeWidth)
            )

            val angleRad = Math.toRadians((startAngle + sweepAngle / 2).toDouble())
            val labelRadius = radius + 30f
            val labelX = (center.x + labelRadius * cos(angleRad)).toFloat()
            val labelY = (center.y + labelRadius * sin(angleRad)).toFloat()

            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    "${slice.icon ?: ""} ${(slice.value / total * 100).toInt()}%",
                    labelX,
                    labelY,
                    Paint().apply {
                        textSize = 36f
                        color = Color.Black
                        textAlign = Paint.Align.CENTER
                        isFakeBoldText = true
                    }
                )
            }

            startAngle += sweepAngle.toFloat()
        }
    }
}