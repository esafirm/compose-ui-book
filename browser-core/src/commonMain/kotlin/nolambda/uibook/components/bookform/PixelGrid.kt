package nolambda.uibook.components.bookform

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

@Composable
fun PixelGrid() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        val gridSize = 12

        repeat(gridSize) {
            val endHeight = canvasHeight / gridSize * it
            val endWidth = canvasWidth / gridSize * it

            drawLine(
                start = Offset(x = endWidth, y = 0F),
                end = Offset(x = endWidth, y = canvasHeight),
                color = Color.Gray,
                alpha = 0.2F
            )

            drawLine(
                start = Offset(x = canvasWidth, y = endHeight),
                end = Offset(x = 0F, y = endHeight),
                color = Color.Gray,
                alpha = 0.2F
            )
        }
    }
}