package com.coppel.technicaltest.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LoginBackground() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {

            drawRect(
                color = Color.White,
                topLeft = Offset(0f, size.height / 2),
                size = Size(size.width, size.height / 2)
            )

            val squareSize = size.width
            val topLeft = Offset(
                (size.width - squareSize) / 2,
                (size.height - squareSize) / 2
            )

            rotate(degrees = 45f, pivot = center) {
                drawRoundRect(
                    color = Color.White,
                    topLeft = topLeft,
                    size = Size(squareSize, squareSize),
                    cornerRadius = CornerRadius(80f, 80f)
                )
            }

        }
    }
}

@Composable
@Preview(showBackground = true)
fun LoginBackgroundPreview() {
    LoginBackground()
}