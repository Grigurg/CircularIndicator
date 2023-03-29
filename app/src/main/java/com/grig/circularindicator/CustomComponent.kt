package com.grig.circularindicator

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
    indicatorStrokeCap: StrokeCap = StrokeCap.Round,
    sweepAngle: Float = 240f,
    foregroundIndicatorColor: Color = MaterialTheme.colors.primary,
    foregroundIndicatorStrokeWidth: Float = 100f,
    bigTextSuffix: String = "GB",
    smallTextStyle: TextStyle = MaterialTheme.typography.h6.copy(
        color = MaterialTheme.colors.onSurface.copy(
            alpha = 0.3f
        )
    ),
    bigTextStyle: TextStyle = MaterialTheme.typography.h3,
    smallText: String = "Remaining",
    duration: Int = 1000,
) {
    var allowedIndicatorValue by remember {
        mutableStateOf(maxIndicatorValue)
    }

    allowedIndicatorValue =
        if (indicatorValue <= maxIndicatorValue) indicatorValue else maxIndicatorValue

    val startAngle = (360 - sweepAngle) / 2 + 90 // calculate start angle

    var animatedIndicatorValue by remember {
        mutableStateOf(0f)
    }
    LaunchedEffect(key1 = allowedIndicatorValue) {
        animatedIndicatorValue = allowedIndicatorValue.toFloat()
    }

    val portion = (animatedIndicatorValue / maxIndicatorValue)

    val animatedSweepAngle by animateFloatAsState(
        targetValue = (sweepAngle * portion),
        animationSpec = tween(duration)
    )

    val animatedReceiveValue by animateIntAsState(
        targetValue = allowedIndicatorValue,
        animationSpec = tween(duration)
    )

//    val animatedBigTextColor by animateColorAsState(
//        targetValue = if (allowedIndicatorValue == 0) MaterialTheme.colors.onSurface.copy(
//            alpha = 0.3f
//        ) else bigTextStyle.color,
//        animationSpec = tween(duration)
//    )

    val animatedBigTextColor by animateColorAsState(
        targetValue = if (allowedIndicatorValue == 0) MaterialTheme.colors.onSurface.copy(alpha = 0.3f)
         else MaterialTheme.colors.onSurface.copy(0.8f),
        animationSpec = tween(duration)
    )

    Box(
        modifier = Modifier
            .size(canvasSize)
            .drawBehind {
                val componentSize = size / 1.25f
                backgroundIndicator(
                    componentSize = componentSize,
                    indicatorColor = indicatorColor,
                    indicatorStrokeWidth = indicatorStrokeWidth,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    indicatorStrokeCap = indicatorStrokeCap
                )
                foregroundIndicator(
                    componentSize = componentSize,
                    indicatorColor = foregroundIndicatorColor,
                    indicatorStrokeWidth = foregroundIndicatorStrokeWidth,
                    startAngle = startAngle,
//                sweepAngle = sweepAngle
                    sweepAngle = animatedSweepAngle,
                    indicatorStrokeCap = indicatorStrokeCap
                )
            },
        contentAlignment = Alignment.Center,
    ) {
        EmbeddedElements(
            bigText = animatedReceiveValue,
            bigTextStyle = bigTextStyle.copy(color = animatedBigTextColor),
            bigTextSuffix = bigTextSuffix,
            smallText = smallText,
            smallTextStyle = smallTextStyle
        )
    }
}

fun DrawScope.backgroundIndicator(
    componentSize: Size,
    indicatorColor: Color,
    indicatorStrokeWidth: Float,
    startAngle: Float,
    sweepAngle: Float,
    indicatorStrokeCap: StrokeCap
) {
    drawArc(
        size = componentSize,
        color = indicatorColor,
        startAngle = startAngle,
        sweepAngle = sweepAngle,
        useCenter = false,
        style = Stroke(
            width = indicatorStrokeWidth,
            cap = indicatorStrokeCap
        ),
        topLeft = Offset(
            x = (size.width - componentSize.width) / 2,
            y = (size.height - componentSize.height) / 2
        )
    )
}

fun DrawScope.foregroundIndicator(
    componentSize: Size,
    indicatorColor: Color,
    indicatorStrokeWidth: Float,
    sweepAngle: Float,
    startAngle: Float,
    indicatorStrokeCap: StrokeCap
) {
    drawArc(
        size = componentSize,
        color = indicatorColor,
        startAngle = startAngle,
        sweepAngle = sweepAngle,
        useCenter = false,
        style = Stroke(
            width = indicatorStrokeWidth,
            cap = indicatorStrokeCap
        ),
        topLeft = Offset(
            x = (size.width - componentSize.width) / 2,
            y = (size.height - componentSize.height) / 2
        )
    )
}

@Composable
fun EmbeddedElements(
    bigText: Int,
    bigTextStyle: TextStyle,
    bigTextSuffix: String,
    smallText: String,
    smallTextStyle: TextStyle,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = smallText,
            style = smallTextStyle.copy(textAlign = TextAlign.Center)
        )
        Text(
            text = "$bigText ${bigTextSuffix.take(2)}",
            style = bigTextStyle.copy(textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CustomComponentPreview() {
    CustomComponent(indicatorValue = 30)
}