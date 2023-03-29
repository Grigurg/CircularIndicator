package com.grig.circularindicator

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CustomComponent(
    canvasSize: Dp = 300.dp,
    indicatorValue: Int = 0,
    maxIndicatorValue: Int = 100,
    indicatorColor: Color = MaterialTheme.colors.onSurface.copy(alpha = 0.1f),
    indicatorStrokeWidth: Float = 100f,
    sweepAngle: Float = 240f
) {
    Column(modifier = Modifier
        .size(canvasSize)
        .drawBehind {
            val componentSize = size / 1.25f
            backgroundIndicator(
                componentSize = componentSize,
                indicatorColor = indicatorColor,
                indicatorStrokeWidth = indicatorStrokeWidth,
                sweepAngle = sweepAngle
            )
        }
    ) {
    }
}

fun DrawScope.backgroundIndicator(
    componentSize: Size,
    indicatorColor: Color,
    indicatorStrokeWidth: Float,
    sweepAngle: Float
) {
    val startAngle = (360 - sweepAngle) / 2 + 90 // calculate start angle
    drawArc(
        size = componentSize,
        color = indicatorColor,
        startAngle = startAngle,
        sweepAngle = sweepAngle,
        useCenter = false,
        style = Stroke(
            width = indicatorStrokeWidth,
            cap = StrokeCap.Round
        ),
        topLeft = Offset(
            x = (size.width - componentSize.width) / 2,
            y = (size.height - componentSize.height) / 2
        )
    )
}

@Preview(showBackground = true)
@Composable
fun CustomComponentPreview() {
    CustomComponent()
}