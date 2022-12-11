package nolambda.uibook.frame

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

/**
 * Composable function that wrap the content with a device frame.
 *
 * @param device The device info that will be used to wrap the content.
 * @param modifier Modifier to be applied to the device frame.
 * @param content The content that will be wrapped by the device frame.
 */
@Composable
fun DeviceFrame(
    device: Device,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
) {
    with(LocalDensity.current) {
        val screenPath = Path().apply(device.screenPath)
        val shape = GenericShape { _, _ -> this.apply(device.screenPath) }

        val resolution = device.resolution

        Box(modifier = modifier) {
            device.frame(
                Modifier.size(
                    width = resolution.frameSize.width.toDp(),
                    height = resolution.frameSize.height.toDp()
                )
            )

            // Represent the screen padding
            Box(
                modifier = Modifier
                    .background(shape = shape, color = Color.White)
                    .clip(shape)
                    .padding(
                        top = screenPath.getBounds().top.toDp(),
                        start = screenPath.getBounds().left.toDp()
                    )
            ) {
                // Inside the screen
                Box(
                    modifier = Modifier
                        .width(resolution.screenSize.width.toDp())
                        .requiredHeight(resolution.screenSize.height.toDp())
                ) {
                    content()
                }
            }
        }
    }
}
