package nolambda.uibook.frame.utils

import androidx.compose.ui.geometry.Rect

/**
 * Utility function to get the bounds of a path.
 */
object RectFactory {

    /**
     * Create Rect from Left Top Width Height
     */
    internal fun LTWH(
        left: Float,
        top: Float,
        width: Float,
        height: Float,
    ): Rect = Rect(
        left = left,
        top = top,
        right = left + width,
        bottom = top + height,
    )
}
