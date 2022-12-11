package nolambda.uibook.frame.generic.factory

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import nolambda.uibook.frame.generic.base.Inset
import nolambda.uibook.frame.generic.base.SideButtonSide
import nolambda.uibook.frame.utils.drawCamera
import nolambda.uibook.frame.utils.drawDeviceBody
import nolambda.uibook.frame.utils.drawSideButtons

object GenericPhoneDeviceFrameFactory {

    private val DEFAULT_BUTTON_COLOR = Color(0xff121515)
    private val DEFAULT_OUTER_BODY_COLOR = Color(0xff3A4245)
    private val DEFAULT_OUTER_BODY_RADIUS = 40.dp
    private val DEFAULT_INNER_BODY_COLOR = Color(0xff121515)

    private val DEFAULT_CAMERA_BORDER_COLOR = Color(0xff262C2D)
    private val DEFAULT_CAMERA_INNER_COLOR = Color(0xff121515)
    private val DEFAULT_CAMERA_REFLECT_COLOR = Color(0xff465256)
    private val DEFAULT_CAMERA_RADIUS = 8.dp
    private val DEFAULT_CAMERA_BORDER_WIDTH = 5.dp
    private val DEFAULT_INNER_BODY_RADIUS = 35.dp
    private val DEFAULT_SCREEN_RADIUS = 10.dp
    private val DEFAULT_BUTTON_WIDTH = 4.dp

    private val DEFAULT_RIGHT_SIDE_BUTTONS_GAPS_AND_SIZES = listOf(100.dp, 80.dp, 15.dp, 80.dp)
    private val DEFAULT_TOP_SIDE_BUTTONS_GAPS_AND_SIZES = listOf(50.dp, 80.dp)

    private val DEFAULT_INNER_BODY_INSETS = Inset(6.dp, 6.dp)
    private val DEFAULT_SCREEN_INSETS = Inset(15.dp, 80.dp)

    fun create(
        outerBodyColor: Color = DEFAULT_OUTER_BODY_COLOR,
        innerBodyColor: Color = DEFAULT_INNER_BODY_COLOR,
        outerBodyRadius: Dp = DEFAULT_OUTER_BODY_RADIUS,
        innerBodyRadius: Dp = DEFAULT_INNER_BODY_RADIUS,
        innerBodyInsets: Inset = DEFAULT_INNER_BODY_INSETS,
        screenInsets: Inset = DEFAULT_SCREEN_INSETS,
        buttonWidth: Dp = DEFAULT_BUTTON_WIDTH,
        buttonColor: Color = DEFAULT_BUTTON_COLOR,
        screenRadius: Dp = DEFAULT_SCREEN_RADIUS,
        rightSideButtonsGapsAndSizes: List<Dp> = DEFAULT_RIGHT_SIDE_BUTTONS_GAPS_AND_SIZES,
        topSideButtonsGapsAndSizes: List<Dp> = DEFAULT_TOP_SIDE_BUTTONS_GAPS_AND_SIZES,
        cameraBorderColor: Color = DEFAULT_CAMERA_BORDER_COLOR,
        cameraInnerColor: Color = DEFAULT_CAMERA_INNER_COLOR,
        cameraReflectColor: Color = DEFAULT_CAMERA_REFLECT_COLOR,
        cameraRadius: Dp = DEFAULT_CAMERA_RADIUS,
        cameraBorderWidth: Dp = DEFAULT_CAMERA_BORDER_WIDTH,
    ): @Composable (modifier: Modifier) -> Unit = { modifier ->
        Canvas(modifier = modifier) {

            val bounds = Rect(
                left = 0f,
                top = buttonWidth.value,
                right = size.width - buttonWidth.value,
                bottom = size.height - buttonWidth.value
            )

            // Draw top side button
            drawSideButtons(
                bounds = bounds,
                buttonWidth = buttonWidth,
                color = buttonColor,
                gapsAndSizes = topSideButtonsGapsAndSizes,
                inverted = true,
                side = SideButtonSide.TOP
            )

            // Draw right side button
            drawSideButtons(
                bounds = bounds,
                buttonWidth = buttonWidth,
                color = buttonColor,
                gapsAndSizes = rightSideButtonsGapsAndSizes,
                inverted = false,
                side = SideButtonSide.RIGHT
            )

            // Draw body
            drawDeviceBody(
                bounds = bounds,
                innerBodyColor = innerBodyColor,
                innerBodyInsets = innerBodyInsets,
                innerBodyRadius = innerBodyRadius,
                outerBodyColor = outerBodyColor,
                outerBodyRadius = outerBodyRadius,
            )

            // Draw camera
            drawCamera(
                borderColor = cameraBorderColor,
                borderWidth = cameraBorderWidth,
                innerColor = cameraInnerColor,
                reflectColor = cameraReflectColor,
                radius = cameraRadius,
                center = Offset(
                    x = ((size.width - buttonWidth.toPx()) * 0.5).toFloat(),
                    y = buttonWidth.toPx() + innerBodyInsets.vertical.toPx() + (screenInsets.vertical.toPx() / 2),
                ),
            )
        }
    }
}
