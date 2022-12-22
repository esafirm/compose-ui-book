package nolambda.uibook.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

/**
 * Clickable version that doesn't have any indication
 */
@Composable
fun Modifier.simpleClick(onClick: () -> Unit): Modifier {
    return clickable(
        onClick = onClick,
        indication = null,
        interactionSource = remember { MutableInteractionSource() }
    )
}

/**
 * Clickable version that shows ripple
 */
@Composable
fun Modifier.rippleClick(onClick: () -> Unit): Modifier {
    return clickable(
        onClick = onClick,
        indication = rememberRipple(),
        interactionSource = remember { MutableInteractionSource() }
    )
}
