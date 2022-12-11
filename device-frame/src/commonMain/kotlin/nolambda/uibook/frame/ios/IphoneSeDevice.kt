package nolambda.uibook.frame.ios

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.unit.dp
import nolambda.uibook.frame.Device
import nolambda.uibook.frame.DevicePlatform
import nolambda.uibook.frame.DeviceSize
import nolambda.uibook.frame.Resolution

private val iPhoneSeFrame = @Composable { modifier: Modifier ->

    Canvas(modifier = modifier) {
        drawPath(
            path = Path().apply {
                moveTo(884.74f, 369.179f)
                lineTo(886.827f, 369.179f)
                cubicTo(889.132f, 369.179f, 891f, 370.356f, 891f, 371.808f)
                lineTo(891f, 497.984f)
                cubicTo(891f, 499.436f, 889.132f, 500.613f, 886.827f, 500.613f)
                lineTo(884.74f, 500.613f)
                lineTo(884.74f, 369.179f)
                close()
            },
            color = Color(0xff121515)
        )

        drawPath(
            path = Path().apply {
                moveTo(0F, 538.165F)
                cubicTo(0F, 535.861F, 1.86845F, 533.993F, 4.1733F, 533.993F)
                lineTo(6.25995F, 533.993F)
                lineTo(6.25995F, 665.427F)
                lineTo(4.1733F, 665.427F)
                cubicTo(1.86845F, 665.427F, 0F, 663.558F, 0F, 661.254F)
                lineTo(0F, 538.165F)
                close()
            },
            color = Color(0xff121515)
        )

        drawPath(
            path = Path().apply {
                moveTo(0F, 373.352F)
                cubicTo(0F, 371.048F, 1.86845F, 369.179F, 4.1733F, 369.179F)
                lineTo(6.25995F, 369.179F)
                lineTo(6.25995F, 500.613F)
                lineTo(4.1733F, 500.613F)
                cubicTo(1.86845F, 500.613F, 0F, 498.745F, 0F, 496.44F)
                lineTo(0F, 373.352F)
                close()
            },
            color = Color(0xff121515)
        )

        drawPath(
            path = Path().apply {
                moveTo(0F, 246.091F)
                cubicTo(0F, 243.787F, 1.86845F, 241.918F, 4.1733F, 241.918F)
                lineTo(6.25995F, 241.918F)
                lineTo(6.25995F, 310.765F)
                lineTo(4.1733F, 310.765F)
                cubicTo(1.86845F, 310.765F, 0F, 308.897F, 0F, 306.592F)
                lineTo(0F, 246.091F)
                close()
            },
            color = Color(0xff121515)
        )

        drawPath(
            path = Path().apply {
                moveTo(6.25977F, 133.434F)
                cubicTo(6.25977F, 60.8448F, 65.116F, 2F, 137.719F, 2F)
                lineTo(753.281F, 2F)
                cubicTo(825.884F, 2F, 884.74F, 60.8448F, 884.74F, 133.434F)
                lineTo(884.74F, 1660.57F)
                cubicTo(884.74F, 1733.16F, 825.884F, 1792F, 753.281F, 1792F)
                lineTo(137.719F, 1792F)
                cubicTo(65.116F, 1792F, 6.25977F, 1733.16F, 6.25977F, 1660.57F)
                lineTo(6.25977F, 133.434F)
                close()
            },
            color = Color(0xff3A4245)
        )

        drawPath(
            path = Path().apply {
                moveTo(16.6934F, 133.433F)
                cubicTo(16.6934F, 66.6058F, 70.8785F, 12.4312F, 137.719F, 12.4312F)
                lineTo(753.281F, 12.4312F)
                cubicTo(820.122F, 12.4312F, 874.307F, 66.6058F, 874.307F, 133.433F)
                lineTo(874.307F, 1660.57F)
                cubicTo(874.307F, 1727.39F, 820.122F, 1781.57F, 753.281F, 1781.57F)
                lineTo(137.719F, 1781.57F)
                cubicTo(70.8785F, 1781.57F, 16.6934F, 1727.39F, 16.6934F, 1660.57F)
                lineTo(16.6934F, 133.433F)
                close()
            },
            color = Color(0xff121515)
        )

        drawPath(
            path = Path().apply {
                moveTo(365.164F, 118.83F)
                cubicTo(365.164F, 115.373F, 367.966F, 112.571F, 371.423F, 112.571F)
                lineTo(519.577F, 112.571F)
                cubicTo(523.034F, 112.571F, 525.836F, 115.373F, 525.836F, 118.83F)
                lineTo(525.836F, 118.83F)
                cubicTo(525.836F, 122.286F, 523.034F, 125.089F, 519.577F, 125.089F)
                lineTo(371.423F, 125.089F)
                cubicTo(367.966F, 125.089F, 365.164F, 122.286F, 365.164F, 118.83F)
                lineTo(365.164F, 118.83F)
                close()
            },
            color = Color(0xff262C2D)
        )

        drawPath(
            path = Path().apply {
                moveTo(294.218F, 135.52F)
                cubicTo(303.437F, 135.52F, 310.911F, 128.047F, 310.911F, 118.83F)
                cubicTo(310.911F, 109.612F, 303.437F, 102.14F, 294.218F, 102.14F)
                cubicTo(284.998F, 102.14F, 277.524F, 109.612F, 277.524F, 118.83F)
                cubicTo(277.524F, 128.047F, 284.998F, 135.52F, 294.218F, 135.52F)
                close()
            },
            color = Color(0xff262C2D)
        )

        drawPath(
            path = Path().apply {
                moveTo(294.218F, 129.261F)
                cubicTo(299.98F, 129.261F, 304.651F, 124.591F, 304.651F, 118.83F)
                cubicTo(304.651F, 113.069F, 299.98F, 108.399F, 294.218F, 108.399F)
                cubicTo(288.455F, 108.399F, 283.784F, 113.069F, 283.784F, 118.83F)
                cubicTo(283.784F, 124.591F, 288.455F, 129.261F, 294.218F, 129.261F)
                close()
            },
            color = Color(0xff121515)
        )

        drawPath(
            path = Path().apply {
                moveTo(294.218F, 116.744F)
                cubicTo(295.37F, 116.744F, 296.304F, 115.809F, 296.304F, 114.657F)
                cubicTo(296.304F, 113.505F, 295.37F, 112.571F, 294.218F, 112.571F)
                cubicTo(293.065F, 112.571F, 292.131F, 113.505F, 292.131F, 114.657F)
                cubicTo(292.131F, 115.809F, 293.065F, 116.744F, 294.218F, 116.744F)
                close()
            },
            color = Color(0xff636F73)
        )

        drawPath(
            path = Path().apply {
                moveTo(445.5F, 77.105F)
                cubicTo(451.838F, 77.105F, 456.977F, 71.9677F, 456.977F, 65.6306F)
                cubicTo(456.977F, 59.2935F, 451.838F, 54.1562F, 445.5F, 54.1562F)
                cubicTo(439.162F, 54.1562F, 434.023F, 59.2935F, 434.023F, 65.6306F)
                cubicTo(434.023F, 71.9677F, 439.162F, 77.105F, 445.5F, 77.105F)
                close()
            },
            color = Color(0xff262C2D)
        )

        drawPath(
            path = Path().apply {
                moveTo(379.771F, 1686.64F)
                cubicTo(379.771F, 1722.94F, 409.199F, 1752.36F, 445.5F, 1752.36F)
                cubicTo(481.801F, 1752.36F, 511.23F, 1722.94F, 511.23F, 1686.64F)
                cubicTo(511.23F, 1650.35F, 481.801F, 1620.93F, 445.5F, 1620.93F)
                cubicTo(409.199F, 1620.93F, 379.771F, 1650.35F, 379.771F, 1686.64F)
                close()
                moveTo(502.883F, 1686.64F)
                cubicTo(502.883F, 1718.33F, 477.192F, 1744.02F, 445.5F, 1744.02F)
                cubicTo(413.808F, 1744.02F, 388.117F, 1718.33F, 388.117F, 1686.64F)
                cubicTo(388.117F, 1654.96F, 413.808F, 1629.27F, 445.5F, 1629.27F)
                cubicTo(477.192F, 1629.27F, 502.883F, 1654.96F, 502.883F, 1686.64F)
                close()
            },
            color = Color(0xff262C2D)
        )

        drawPath(
            path = Path().apply {
                moveTo(47.9932F, 196.935F)
                cubicTo(47.9932F, 195.278F, 49.3363F, 193.935F, 50.9932F, 193.935F)
                lineTo(840.007F, 193.935F)
                cubicTo(841.664F, 193.935F, 843.007F, 195.278F, 843.007F, 196.935F)
                lineTo(843.007F, 1594.98F)
                cubicTo(843.007F, 1596.64F, 841.664F, 1597.98F, 840.007F, 1597.98F)
                lineTo(50.9932F, 1597.98F)
                cubicTo(49.3363F, 1597.98F, 47.9932F, 1596.64F, 47.9932F, 1594.98F)
                lineTo(47.9932F, 196.935F)
                close()
            },
            color = Color(0xff262C2D)
        )
    }
}

private val iPhoneSeScreen: Path.() -> Unit = {
    moveTo(836.747F, 198.193F)
    lineTo(54.2529F, 198.193F)
    lineTo(54.2529F, 1589.72F)
    lineTo(836.747F, 1589.72F)
    lineTo(836.747F, 198.193F)
    close()

    fillType = PathFillType.EvenOdd
}

val iPhoneSE = Device(
    name = "iPhone SE",
    resolution = Resolution(
        screenSize = DeviceSize.fromPathFunc(iPhoneSeScreen),
        frameSize = DeviceSize(
            width = 891,
            height = 1790,
        )
    ),
    platform = DevicePlatform.IOS,
    frame = iPhoneSeFrame,
    screenPath = iPhoneSeScreen,
    safeArea = PaddingValues(top = 20.dp),
    rotatedSafeArea = PaddingValues(top = 20.dp)
)
