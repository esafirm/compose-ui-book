package nolambda.uibook.components.bookform

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun PixelGrid(
    gridSize: Dp = 8.dp,
    gridColor: Color = Color.Gray,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = Modifier.fillMaxSize().composed { modifier }) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        val gridSizeInPx = gridSize.toPx()

        // Draw vertical lines
        repeat((canvasWidth / gridSizeInPx).toInt()) { gridCount ->
            val currentX = gridCount * gridSizeInPx
            drawLine(
                start = Offset(x = currentX, y = 0F),
                end = Offset(x = currentX, y = canvasHeight),
                color = gridColor,
                alpha = 0.2F
            )
        }

        // Draw horizontal lines
        repeat((canvasHeight / gridSizeInPx).toInt()) { gridCount ->
            val currentY = gridCount * gridSizeInPx
            drawLine(
                start = Offset(x = canvasWidth, y = currentY),
                end = Offset(x = 0F, y = currentY),
                color = gridColor,
                alpha = 0.2F
            )
        }
    }
}
