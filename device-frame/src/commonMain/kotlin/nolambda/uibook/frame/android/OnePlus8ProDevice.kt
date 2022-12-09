package nolambda.uibook.frame.android

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

private val onePlus8ProFrame = @Composable { modifier: Modifier ->
    Canvas(modifier = modifier) {

        drawPath(
            path = Path().apply {
                moveTo(6.5205F, 675.949F)
                lineTo(4.34704F, 675.949F)
                cubicTo(1.94629F, 675.949F, 0F, 673.934F, 0F, 671.447F)
                lineTo(0.000118933F, 460.931F)
                cubicTo(0.000119151F, 458.445F, 1.94631F, 456.429F, 4.34706F, 456.429F)
                lineTo(6.52053F, 456.429F)
                lineTo(6.5205F, 675.949F)
                close()
            },
            color = Color(0xff121515)
        )

        drawPath(
            path = Path().apply {
                moveTo(845.479F, 654.214F)
                lineTo(847.653F, 654.214F)
                cubicTo(850.054F, 654.214F, 852F, 656.23F, 852F, 658.717F)
                lineTo(852F, 784.467F)
                cubicTo(852F, 786.954F, 850.054F, 788.969F, 847.653F, 788.969F)
                lineTo(845.479F, 788.969F)
                lineTo(845.479F, 654.214F)
                close()
            },
            color = Color(0xff121515)
        )

        drawPath(
            path = Path().apply {
                moveTo(845.479F, 471.643F)
                lineTo(847.653F, 471.643F)
                cubicTo(850.054F, 471.643F, 852F, 473.659F, 852F, 476.145F)
                lineTo(852F, 538.865F)
                cubicTo(852F, 541.352F, 850.054F, 543.368F, 847.653F, 543.368F)
                lineTo(845.479F, 543.368F)
                lineTo(845.479F, 471.643F)
                close()
            },
            color = Color(0xff121515)
        )

        drawPath(
            path = Path().apply {
                moveTo(6.5205F, 147.796F)
                cubicTo(6.5205F, 90.8783F, 6.5205F, 62.4195F, 19.3318F, 41.5134F)
                cubicTo(26.5004F, 29.8153F, 36.3358F, 19.9799F, 48.0339F, 12.8113F)
                cubicTo(68.94F, 0F, 97.3988F, 0F, 154.316F, 0F)
                lineTo(697.684F, 0F)
                cubicTo(754.601F, 0F, 783.06F, 0F, 803.966F, 12.8113F)
                cubicTo(815.664F, 19.9799F, 825.5F, 29.8153F, 832.668F, 41.5134F)
                cubicTo(845.48F, 62.4195F, 845.48F, 90.8783F, 845.48F, 147.796F)
                lineTo(845.48F, 1717.04F)
                cubicTo(845.48F, 1773.96F, 845.48F, 1802.42F, 832.668F, 1823.32F)
                cubicTo(825.5F, 1835.02F, 815.664F, 1844.86F, 803.966F, 1852.03F)
                cubicTo(783.06F, 1864.84F, 754.601F, 1864.84F, 697.684F, 1864.84F)
                lineTo(154.316F, 1864.84F)
                cubicTo(97.3988F, 1864.84F, 68.94F, 1864.84F, 48.0339F, 1852.03F)
                cubicTo(36.3358F, 1844.86F, 26.5004F, 1835.02F, 19.3318F, 1823.32F)
                cubicTo(6.5205F, 1802.42F, 6.5205F, 1773.96F, 6.5205F, 1717.04F)
                lineTo(6.5205F, 147.796F)
                close()
            },
            color = Color(0xff3A4245)
        )

        drawPath(
            path = Path().apply {
                moveTo(10.8672F, 142.362F)
                cubicTo(10.8672F, 92.5595F, 10.8672F, 67.6581F, 22.0771F, 49.3652F)
                cubicTo(28.3496F, 39.1294F, 36.9556F, 30.5234F, 47.1914F, 24.2509F)
                cubicTo(65.4843F, 13.041F, 90.3857F, 13.041F, 140.189F, 13.041F)
                lineTo(711.811F, 13.041F)
                cubicTo(761.614F, 13.041F, 786.515F, 13.041F, 804.808F, 24.2509F)
                cubicTo(815.044F, 30.5234F, 823.65F, 39.1294F, 829.923F, 49.3652F)
                cubicTo(841.132F, 67.6581F, 841.132F, 92.5595F, 841.132F, 142.362F)
                lineTo(841.132F, 1722.47F)
                cubicTo(841.132F, 1772.28F, 841.132F, 1797.18F, 829.923F, 1815.47F)
                cubicTo(823.65F, 1825.71F, 815.044F, 1834.31F, 804.808F, 1840.59F)
                cubicTo(786.515F, 1851.8F, 761.614F, 1851.8F, 711.811F, 1851.8F)
                lineTo(140.189F, 1851.8F)
                cubicTo(90.3857F, 1851.8F, 65.4843F, 1851.8F, 47.1914F, 1840.59F)
                cubicTo(36.9556F, 1834.31F, 28.3496F, 1825.71F, 22.0771F, 1815.47F)
                cubicTo(10.8672F, 1797.18F, 10.8672F, 1772.28F, 10.8672F, 1722.47F)
                lineTo(10.8672F, 142.362F)
                close()
            },
            color = Color(0xff121515)
        )

        drawPath(
            path = Path().apply {
                moveTo(319.5F, 26.0815F)
                cubicTo(315.53F, 26.0815F, 315.186F, 20.6429F, 311.845F, 19.6997F)
                cubicTo(311.472F, 19.5945F, 311.295F, 19.1147F, 311.602F, 18.8783F)
                cubicTo(312.429F, 18.241F, 313.791F, 17.3877F, 315.153F, 17.3877F)
                lineTo(536.847F, 17.3877F)
                cubicTo(538.209F, 17.3877F, 539.571F, 18.241F, 540.398F, 18.8783F)
                cubicTo(540.705F, 19.1147F, 540.528F, 19.5945F, 540.155F, 19.6997F)
                cubicTo(536.814F, 20.6429F, 536.47F, 26.0815F, 532.5F, 26.0815F)
                lineTo(319.5F, 26.0815F)
                close()
            },
            color = Color(0xff262C2D)
        )

        drawPath(
            path = Path().apply {
                moveTo(108.673F, 110.847F)
                cubicTo(120.677F, 110.847F, 130.408F, 101.116F, 130.408F, 89.1121F)
                cubicTo(130.408F, 77.1084F, 120.677F, 67.3774F, 108.673F, 67.3774F)
                cubicTo(96.6694F, 67.3774F, 86.9385F, 77.1084F, 86.9385F, 89.1121F)
                cubicTo(86.9385F, 101.116F, 96.6694F, 110.847F, 108.673F, 110.847F)
                close()
            },
            color = Color(0xff262C2D)
        )

        drawPath(
            path = Path().apply {
                moveTo(108.673F, 102.696F)
                cubicTo(116.175F, 102.696F, 122.257F, 96.6144F, 122.257F, 89.112F)
                cubicTo(122.257F, 81.6097F, 116.175F, 75.5278F, 108.673F, 75.5278F)
                cubicTo(101.171F, 75.5278F, 95.0889F, 81.6097F, 95.0889F, 89.112F)
                cubicTo(95.0889F, 96.6144F, 101.171F, 102.696F, 108.673F, 102.696F)
                close()
            },
            color = Color(0xff121515)
        )

        drawPath(
            path = Path().apply {
                moveTo(108.673F, 86.3951F)
                cubicTo(110.173F, 86.3951F, 111.39F, 85.1787F, 111.39F, 83.6783F)
                cubicTo(111.39F, 82.1778F, 110.173F, 80.9614F, 108.673F, 80.9614F)
                cubicTo(107.172F, 80.9614F, 105.956F, 82.1778F, 105.956F, 83.6783F)
                cubicTo(105.956F, 85.1787F, 107.172F, 86.3951F, 108.673F, 86.3951F)
                close()
            },
            color = Color(0xff636F73)
        )
    }
}

private val onePlus8ProScreen: Path.() -> Unit = {
    moveTo(30.3263f, 75.7513f)
    cubicTo(21.7354f, 91.0915f, 21.7354f, 111.551f, 21.7354f, 152.469f)
    lineTo(21.7354f, 1708.02f)
    cubicTo(21.7354f, 1748.94f, 21.7354f, 1769.4f, 30.3263f, 1784.74f)
    cubicTo(36.3978f, 1795.58f, 45.3492f, 1804.53f, 56.1908f, 1810.6f)
    cubicTo(71.531f, 1819.19f, 91.9901f, 1819.19f, 132.908f, 1819.19f)
    lineTo(719.093f, 1819.19f)
    cubicTo(760.011f, 1819.19f, 780.47f, 1819.19f, 795.81f, 1810.6f)
    cubicTo(806.652f, 1804.53f, 815.603f, 1795.58f, 821.675f, 1784.74f)
    cubicTo(830.266f, 1769.4f, 830.266f, 1748.94f, 830.266f, 1708.02f)
    lineTo(830.266f, 152.469f)
    cubicTo(830.266f, 111.551f, 830.266f, 91.0915f, 821.675f, 75.7513f)
    cubicTo(815.603f, 64.9098f, 806.652f, 55.9584f, 795.81f, 49.8868f)
    cubicTo(780.47f, 41.2959f, 760.011f, 41.2959f, 719.093f, 41.2959f)
    lineTo(132.908f, 41.2959f)
    cubicTo(91.9901f, 41.2959f, 71.531f, 41.2959f, 56.1908f, 49.8868f)
    cubicTo(45.3492f, 55.9584f, 36.3978f, 64.9098f, 30.3263f, 75.7513f)
    close()
    moveTo(130.47f, 88.7347f)
    cubicTo(130.47f, 100.738f, 120.739f, 110.469f, 108.736f, 110.469f)
    cubicTo(96.7319f, 110.469f, 87.001f, 100.738f, 87.001f, 88.7347f)
    cubicTo(87.001f, 76.731f, 96.7319f, 67f, 108.736f, 67f)
    cubicTo(120.739f, 67f, 130.47f, 76.731f, 130.47f, 88.7347f)
    close()
}

val onePlus8Pro = Device(
    name = "OnePlus 8 Pro",
    resolution = Resolution(
        screenSize = DeviceSize(
            width = 360,
            height = 800
        ),
        frameSize = DeviceSize(
            width = 852,
            height = 1865
        )
    ),
    platform = DevicePlatform.ANDROID,
    frame = onePlus8ProFrame,
    screenPath = onePlus8ProScreen,
    safeArea = PaddingValues(top = 40.dp, bottom = 20.dp),
    rotatedSafeArea = PaddingValues(top = 24.dp, start = 40.dp, end = 40.dp)
)
