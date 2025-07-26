package com.example.analytics.donutChart

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import kotlin.math.cos
import kotlin.math.sin


@Composable
fun DonutChart(
    slices: List<ChartSlice>,
    modifier: Modifier = Modifier,
    strokeWidth: Float = 40f
) {
    val total = slices.sumOf { it.value }
    if (total == 0.0) return

    val sweepAngles = slices.map { (it.value / total) * 360f }

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val radius = size.minDimension / 2
            val center = Offset(size.width / 2, size.height / 2)
            var startAngle = -90f

            slices.forEachIndexed { index, slice ->
                val sweepAngle = sweepAngles[index]
                var arcColor = slice.color ?: generateColorFromLabel(slice.label)

                drawArc(
                    color = arcColor,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle.toFloat(),
                    useCenter = false,
                    topLeft = Offset(center.x - radius, center.y - radius),
                    size = Size(radius * 2, radius * 2),
                    style = Stroke(width = strokeWidth)
                )

                val midAngle = startAngle + sweepAngle / 2
                val angleRad = Math.toRadians(midAngle.toDouble())
                val labelRadius = radius + 30f
                val labelX = (center.x + labelRadius * cos(angleRad)).toFloat()
                val labelY = (center.y + labelRadius * sin(angleRad)).toFloat()
                val percent = (slice.value / total * 100).toInt()

                drawContext.canvas.nativeCanvas.apply {
                    val text = "${slice.icon ?: ""} $percent%"
                    val paint = Paint().apply {
                        textSize = 32f
                        color = Color.White.toArgb()
                        textAlign = Paint.Align.CENTER
                        isFakeBoldText = true
                    }
                    val textWidth = paint.measureText(text)
                    drawRect(
                        labelX - textWidth/2 - 8,
                        labelY - 32,
                        labelX + textWidth/2 + 8,
                        labelY + 8,
                        paint
                    )

                    paint.color = Color.Black.toArgb()
                    drawText(text, labelX, labelY, paint)
                }


                startAngle += sweepAngle.toFloat()
            }
        }

    }
}