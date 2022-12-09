package nolambda.uibook.frame.ios

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp
import nolambda.uibook.frame.Device
import nolambda.uibook.frame.DevicePlatform
import nolambda.uibook.frame.DeviceSize
import nolambda.uibook.frame.Resolution

private val iPadProFrame = @Composable { modifier: Modifier ->
    Canvas(modifier = modifier) {

        drawPath(
            path = Path().apply {
                moveTo(0F, 128.369F)
                cubicTo(0F, 60.4267F, 55.0779F, 5.34875F, 123.02F, 5.34875F)
                lineTo(1612.63F, 5.34875F)
                cubicTo(1680.57F, 5.34875F, 1735.65F, 60.4267F, 1735.65F, 128.369F)
                lineTo(1735.65F, 2289.24F)
                cubicTo(1735.65F, 2357.18F, 1680.57F, 2412.26F, 1612.63F, 2412.26F)
                lineTo(123.02F, 2412.26F)
                cubicTo(55.0779F, 2412.26F, 0F, 2357.18F, 0F, 2289.24F)
                lineTo(0F, 128.369F)
                close()
            },
            color = Color(0xff3A4245)
        )

        drawPath(
            path = Path().apply {
                moveTo(10.6973F, 128.369F)
                cubicTo(10.6973F, 66.3347F, 60.9858F, 16.0461F, 123.02F, 16.0461F)
                lineTo(1612.63F, 16.0461F)
                cubicTo(1674.67F, 16.0461F, 1724.95F, 66.3347F, 1724.95F, 128.369F)
                lineTo(1724.95F, 2289.24F)
                cubicTo(1724.95F, 2351.28F, 1674.67F, 2401.56F, 1612.63F, 2401.56F)
                lineTo(123.02F, 2401.56F)
                cubicTo(60.9858F, 2401.56F, 10.6973F, 2351.28F, 10.6973F, 2289.24F)
                lineTo(10.6973F, 128.369F)
                close()
            },
            color = Color(0xff3A4245)
        )

        drawPath(
            path = Path().apply {
                moveTo(1735.65F, 195.227F)
                cubicTo(1738.61F, 195.227F, 1741F, 196.137F, 1741F, 197.26F)
                lineTo(1741F, 294.82F)
                cubicTo(1741F, 295.942F, 1738.61F, 296.852F, 1735.65F, 296.852F)
                lineTo(1735.65F, 195.227F)
                close()
            },
            color = Color(0xff121515)
        )

        drawPath(
            path = Path().apply {
                moveTo(1735.65F, 425.221F)
                cubicTo(1738.61F, 425.221F, 1741F, 426.131F, 1741F, 427.254F)
                lineTo(1741F, 524.814F)
                cubicTo(1741F, 525.936F, 1738.61F, 526.846F, 1735.65F, 526.846F)
                lineTo(1735.65F, 425.221F)
                close()
            },
            color = Color(0xff121515)
        )

        drawPath(
            path = Path().apply {
                moveTo(1494.96F, 5.34875F)
                cubicTo(1494.96F, 2.39475F, 1496.04F, 0F, 1497.37F, 0F)
                lineTo(1612.9F, 0F)
                cubicTo(1614.23F, 0F, 1615.31F, 2.39475F, 1615.31F, 5.34875F)
                lineTo(1494.96F, 5.34875F)
                close()
            },
            color = Color(0xff121515)
        )
    }
}

private val iPadProScreen: Path.() -> Unit = {
    moveTo(90.9277F, 128.369F)
    lineTo(90.9277F, 2289.24F)
    cubicTo(90.9277F, 2306.97F, 105.296F, 2321.33F, 123.02F, 2321.33F)
    lineTo(1612.63F, 2321.33F)
    cubicTo(1630.36F, 2321.33F, 1644.72F, 2306.97F, 1644.72F, 2289.24F)
    lineTo(1644.72F, 128.369F)
    cubicTo(1644.72F, 110.645F, 1630.36F, 96.2765F, 1612.63F, 96.2765F)
    lineTo(123.02F, 96.2765F)
    cubicTo(105.296F, 96.2765F, 90.9277F, 110.645F, 90.9277F, 128.369F)
    close()
}

val iPadPro = Device(
    name = "iPad Pro",
    resolution = Resolution(
        screenSize = DeviceSize(
            width = 1024,
            height = 1366
        )
    ),
    platform = DevicePlatform.IOS,
    frame = iPadProFrame,
    screenPath = iPadProScreen,
    safeArea = PaddingValues(top = 20.dp),
    rotatedSafeArea = PaddingValues(top = 20.dp)
)
