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

val iPadFrame = @Composable { modifier: Modifier ->
    Canvas(modifier = modifier) {

        drawPath(
            path = Path().apply {
                moveTo(0f, 93.6833f)
                cubicTo(0f, 41.9435f, 41.9435f, 0f, 93.6833f, 0f)
                lineTo(1579.23f, 0f)
                cubicTo(1630.97f, 0f, 1672.92f, 41.9435f, 1672.92f, 93.6833f)
                lineTo(1672.92f, 2315.32f)
                cubicTo(1672.92f, 2367.06f, 1630.97f, 2409f, 1579.23f, 2409f)
                lineTo(93.6833f, 2409f)
                cubicTo(41.9435f, 2409f, 0f, 2367.06f, 0f, 2315.32f)
                lineTo(0f, 93.6833f)
                close()
            },
            color = Color(0xff3A4245)
        )

        drawPath(
            path = Path().apply {
                moveTo(10.707F, 96.36F)
                cubicTo(10.707F, 49.055F, 49.0553F, 10.7067F, 96.3604F, 10.7067F)
                lineTo(1576.56F, 10.7067F)
                cubicTo(1623.86F, 10.7067F, 1662.21F, 49.055F, 1662.21F, 96.36F)
                lineTo(1662.21F, 2312.64F)
                cubicTo(1662.21F, 2359.95F, 1623.86F, 2398.29F, 1576.56F, 2398.29F)
                lineTo(96.3604F, 2398.29F)
                cubicTo(49.0553F, 2398.29F, 10.707F, 2359.95F, 10.707F, 2312.64F)
                lineTo(10.707F, 96.36F)
                close()
            },
            color = Color(0xff121515)
        )

        drawPath(
            path = Path().apply {
                moveTo(781.587F, 2316.66F)
                cubicTo(781.587F, 2346.96F, 806.154F, 2371.53F, 836.459F, 2371.53F)
                cubicTo(866.763F, 2371.53F, 891.33F, 2346.96F, 891.33F, 2316.66F)
                cubicTo(891.33F, 2286.35F, 866.763F, 2261.78F, 836.459F, 2261.78F)
                cubicTo(806.154F, 2261.78F, 781.587F, 2286.35F, 781.587F, 2316.66F)
                close()
                moveTo(884.363F, 2316.66F)
                cubicTo(884.363F, 2343.11F, 862.916F, 2364.56F, 836.459F, 2364.56F)
                cubicTo(810.003F, 2364.56F, 788.556F, 2343.11F, 788.556F, 2316.66F)
                cubicTo(788.556F, 2290.2F, 810.003F, 2268.75F, 836.459F, 2268.75F)
                cubicTo(862.916F, 2268.75F, 884.363F, 2290.2F, 884.363F, 2316.66F)
                close()
            },
            color = Color(0xff262C2D)
        )

        drawPath(
            path = Path().apply {
                moveTo(61.5635F, 176.66F)
                cubicTo(61.5635F, 173.703F, 63.9602F, 171.307F, 66.9168F, 171.307F)
                lineTo(1606F, 171.307F)
                cubicTo(1608.96F, 171.307F, 1611.35F, 173.703F, 1611.35F, 176.66F)
                lineTo(1611.35F, 2229.66F)
                cubicTo(1611.35F, 2232.62F, 1608.96F, 2235.02F, 1606F, 2235.02F)
                lineTo(66.9168F, 2235.02F)
                cubicTo(63.9602F, 2235.02F, 61.5635F, 2232.62F, 61.5635F, 2229.66F)
                lineTo(61.5635F, 176.66F)
                close()
            },
            color = Color(0xff262C2D)
        )

        drawPath(
            path = Path().apply {
                moveTo(836.458F, 101.713F)
                cubicTo(843.11F, 101.713F, 848.503F, 96.3206F, 848.503F, 89.6683F)
                cubicTo(848.503F, 83.016F, 843.11F, 77.6233F, 836.458F, 77.6233F)
                cubicTo(829.806F, 77.6233F, 824.413F, 83.016F, 824.413F, 89.6683F)
                cubicTo(824.413F, 96.3206F, 829.806F, 101.713F, 836.458F, 101.713F)
                close()
            },
            color = Color(0xff262C2D)
        )

        drawPath(
            path = Path().apply {
                moveTo(836.458F, 97.1964F)
                cubicTo(840.615F, 97.1964F, 843.986F, 93.8259F, 843.986F, 89.6683F)
                cubicTo(843.986F, 85.5106F, 840.615F, 82.1401F, 836.458F, 82.1401F)
                cubicTo(832.3F, 82.1401F, 828.93F, 85.5106F, 828.93F, 89.6683F)
                cubicTo(828.93F, 93.8259F, 832.3F, 97.1964F, 836.458F, 97.1964F)
                close()
            },
            color = Color(0xff121515)
        )

        drawPath(
            path = Path().apply {
                moveTo(836.458F, 88.1626F)
                cubicTo(837.289F, 88.1626F, 837.963F, 87.4885F, 837.963F, 86.657F)
                cubicTo(837.963F, 85.8255F, 837.289F, 85.1514F, 836.458F, 85.1514F)
                cubicTo(835.626F, 85.1514F, 834.952F, 85.8255F, 834.952F, 86.657F)
                cubicTo(834.952F, 87.4885F, 835.626F, 88.1626F, 836.458F, 88.1626F)
                close()
            },
            color = Color(0xff636F73)
        )
    }
}

val iPadScreen: Path.() -> Unit = {
    moveTo(1606F, 176.66F)
    lineTo(66.9168F, 176.66F)
    lineTo(66.9168F, 2229.66F)
    lineTo(1606F, 2229.66F)
    lineTo(1606F, 176.66F)
    close()
}

val iPad = Device(
    name = "iPad",
    resolution = Resolution(
        screenSize = DeviceSize.fromPathFunc(iPadScreen),
        frameSize = DeviceSize(
            width = 1673,
            height = 2409
        )
    ),
    platform = DevicePlatform.IOS,
    frame = iPadFrame,
    screenPath = iPadScreen,
    safeArea = PaddingValues(top = 20.dp),
    rotatedSafeArea = PaddingValues(top = 20.dp)
)
