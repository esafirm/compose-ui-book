package nolambda.uibook.frame.utils

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp
import nolambda.uibook.frame.generic.base.Inset
import nolambda.uibook.frame.generic.base.SideButtonSide

internal fun DrawScope.drawRectFromRect(rect: Rect, color: Color) {
    drawRect(
        topLeft = rect.topLeft,
        size = rect.size,
        color = color
    )
}

internal fun DrawScope.drawRoundRectFromRect(
    rect: Rect,
    color: Color,
    cornerRadius: CornerRadius,
) {
    drawRoundRect(
        topLeft = rect.topLeft,
        size = rect.size,
        color = color,
        cornerRadius = cornerRadius
    )
}

/**
 * Draw 2 rounded rectangle with padding that will form a device body
 */
internal fun DrawScope.drawDeviceBody(
    bounds: Rect,
    outerBodyColor: Color,
    outerBodyRadius: Dp,
    innerBodyColor: Color,
    innerBodyRadius: Dp,
    innerBodyInsets: Inset,
) {
    // Outer
    drawRoundRectFromRect(
        rect = bounds,
        color = outerBodyColor,
        cornerRadius = CornerRadius(outerBodyRadius.toPx())
    )

    // Inner
    drawRoundRectFromRect(
        rect = bounds.copy(
            left = bounds.left + innerBodyInsets.horizontal.toPx(),
            top = bounds.top + innerBodyInsets.vertical.toPx(),
            right = bounds.right - innerBodyInsets.horizontal.toPx(),
            bottom = bounds.bottom - innerBodyInsets.vertical.toPx()
        ),
        color = innerBodyColor,
        cornerRadius = CornerRadius(innerBodyRadius.toPx())
    )
}

/**
 * Draw 3 circles that will form a camera on device body
 */
internal fun DrawScope.drawCamera(
    center: Offset,
    radius: Dp,
    borderWidth: Dp,
    borderColor: Color,
    innerColor: Color,
    reflectColor: Color,
) {
    drawCircle(
        center = center,
        radius = radius.toPx() + borderWidth.toPx(),
        color = borderColor,
    )
    drawCircle(
        center = center,
        radius = radius.toPx(),
        color = innerColor,
    )
    drawCircle(
        center = center - Offset(0F, radius.toPx() * 0.25F),
        radius = radius.toPx() / 3,
        color = reflectColor,
    )
}

internal fun DrawScope.drawSideButtons(
    bounds: Rect,
    color: Color,
    buttonWidth: Dp,
    side: SideButtonSide,
    inverted: Boolean,
    gapsAndSizes: List<Dp>,
) {
    var x: Float
    var y: Float

    when (side) {
        SideButtonSide.LEFT -> {
            x = bounds.left
            y = bounds.top
        }

        SideButtonSide.RIGHT -> {
            x = bounds.right
            y = bounds.top
        }

        SideButtonSide.TOP -> {
            x = bounds.left
            y = bounds.top
        }

        SideButtonSide.BOTTOM -> {
            x = bounds.left
            y = bounds.bottom
        }
    }

    val buttonRadius = buttonWidth.toPx() / 2

    gapsAndSizes.forEachIndexed { index, current ->
        val isGap = index % 2 == 0
        if (!isGap) {
            val size = current.toPx()

            val rect = when (side) {
                SideButtonSide.LEFT, SideButtonSide.RIGHT ->
                    Rect(
                        left = x - buttonWidth.toPx(),
                        top = if (inverted) bounds.bottom - y - size else y,
                        right = buttonWidth.toPx() * 2,
                        bottom = current.toPx()
                    )

                SideButtonSide.BOTTOM, SideButtonSide.TOP ->
                    Rect(
                        left = if (inverted) bounds.right - x - size else x,
                        top = y - buttonWidth.toPx(),
                        right = current.toPx(),
                        bottom = buttonWidth.toPx() * 2
                    )
            }

            drawRoundRectFromRect(
                rect = rect,
                color = color,
                cornerRadius = CornerRadius(buttonRadius)
            )
        }

        when (side) {
            SideButtonSide.LEFT, SideButtonSide.RIGHT -> {
                y += current.toPx()
            }

            SideButtonSide.TOP, SideButtonSide.BOTTOM -> {
                x += current.toPx()
            }
        }
    }
}
