package nolambda.uibook.components.bookform

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import nolambda.uibook.utils.simpleClick

/**
 * A hack to make drop down menu rendered where [LocalDropdownShow] composition are palced.
 *
 * For now, this is only used in Web Browser
 */
class DropdownMenuShower {

    class OptionalRender(
        val render: Boolean,
        val content: @Composable () -> Unit = {},
    )

    val composable = mutableStateOf(OptionalRender(false))

    fun show(content: @Composable () -> Unit) {
        composable.value = OptionalRender(true, content)
    }
}

/**
 * @see DropdownMenuShower
 */
val LocalDropdownShow = staticCompositionLocalOf { DropdownMenuShower() }

@Composable
actual fun DropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    LocalDropdownShow.current.show {
        AnimatedVisibility(
            visible = expanded,
            modifier = modifier,
            enter = androidx.compose.animation.fadeIn(),
            exit = androidx.compose.animation.fadeOut(),
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
                    .simpleClick(onDismissRequest)
                    .padding(top = 48.dp, end = 24.dp) // to avoid the toolbar
            ) {
                Column(
                    content = content,
                    modifier = Modifier.background(MaterialTheme.colors.background)
                        .clip(RoundedCornerShape(8.dp))
                        .align(alignment = androidx.compose.ui.Alignment.TopEnd)
                )
            }
        }
    }
}

@Composable
actual fun DropdownMenuItem(
    onClick: () -> Unit,
    modifier: Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = Modifier.padding(12.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(),
                onClick = onClick
            )
            .composed { modifier },
        content = content
    )
}
