package nolambda.uibook.frame.utils

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope

fun DrawScope.drawRectFromRect(rect: Rect, color: Color) {
    drawRect(
        topLeft = rect.topLeft,
        size = rect.size,
        color = Color(0xff36454C)
    )
}
