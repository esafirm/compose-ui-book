package nolambda.uibook.utils

import androidx.compose.ui.graphics.Color

/**
 * Parse a color from a string
 *
 * @return the color
 */
internal fun String.toColor(): Color {
    return try {
        Color(removePrefix("#").toLong(16) or 0x00000000FF000000)
    } catch (e: Exception) {
        Color.Unspecified
    }
}
