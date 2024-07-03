package com.cookainno.mobile.ui.screens.generation

import androidx.compose.animation.core.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

fun Modifier.animatedGradientBackground(
    colors: List<Color>,
    durationMillis: Int = 20000
): Modifier = composed {
    val transition = rememberInfiniteTransition(label = "")
    val rotationAngle by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    drawBehind {
        val angleInRad = rotationAngle * (PI / 180f).toFloat()
        val centerX = size.width / 2
        val centerY = size.height / 2
        val radius = sqrt(centerX * centerX + centerY * centerY)

        val startX = centerX + radius * cos(angleInRad)
        val startY = centerY + radius * sin(angleInRad)
        val endX = centerX - radius * cos(angleInRad)
        val endY = centerY - radius * sin(angleInRad)

        val gradient = Brush.linearGradient(
            colors = colors,
            start = Offset(startX, startY),
            end = Offset(endX, endY),
            tileMode = TileMode.Repeated
        )
        drawRect(brush = gradient)
    }
}
