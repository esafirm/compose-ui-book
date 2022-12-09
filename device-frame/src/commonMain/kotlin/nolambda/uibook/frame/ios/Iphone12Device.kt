package nolambda.uibook.frame.ios

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.unit.dp
import nolambda.uibook.frame.Device
import nolambda.uibook.frame.DevicePlatform
import nolambda.uibook.frame.DeviceSize
import nolambda.uibook.frame.Resolution
import nolambda.uibook.frame.drawRectFromRect

private val iPhone12Frame = @Composable {
    Canvas(modifier = Modifier) {

        drawPath(
            path = Path().apply {
                moveTo(866.809F, 454.042F)
                lineTo(869.904F, 454.042F)
                cubicTo(871.614F, 454.042F, 873F, 455.428F, 873F, 457.138F)
                lineTo(873F, 659.394F)
                cubicTo(873F, 661.103F, 871.614F, 662.489F, 869.904F, 662.489F)
                lineTo(866.809F, 662.489F)
                lineTo(866.809F, 454.042F)
                close()
            },
            color = Color(0xff1C3343)
        )

        drawPath(path = Path().apply {
            moveTo(6.19141F, 705.83F)
            lineTo(3.09565F, 705.83F)
            cubicTo(1.38592F, 705.83F, 0F, 704.444F, 0F, 702.734F)
            lineTo(0F, 580.968F)
            cubicTo(0F, 579.258F, 1.38593F, 577.872F, 3.09566F, 577.872F)
            lineTo(6.19142F, 577.872F)
            lineTo(6.19141F, 705.83F)
            close()
        }, color = Color(0xff1C3343))

        drawPath(path = Path().apply {
            moveTo(6.19141F, 454.042F)
            lineTo(3.09565F, 454.042F)
            cubicTo(1.38592F, 454.042F, 0F, 452.656F, 0F, 450.946F)
            lineTo(0F, 329.18F)
            cubicTo(0F, 327.47F, 1.38593F, 326.084F, 3.09566F, 326.084F)
            lineTo(6.19142F, 326.084F)
            lineTo(6.19141F, 454.042F)
            close()
        }, color = Color(0xff213744))

        drawPath(
            path = Path().apply {
                moveTo(6.19141F, 346.723F)
                lineTo(3.09566F, 346.723F)
                cubicTo(1.38592F, 346.723F, 0F, 345.337F, 0F, 343.628F)
                lineTo(0F, 283.777F)
                cubicTo(0F, 282.067F, 1.38593F, 280.681F, 3.09566F, 280.681F)
                lineTo(6.19141F, 280.681F)
                lineTo(6.19141F, 346.723F)
                close()
            }, color = Color(0xff213744)
        )

        drawPath(
            path = Path().apply {
                moveTo(6.19141F, 187.809F)
                cubicTo(6.19141F, 137.871F, 6.19141F, 112.902F, 12.7571F, 92.6946F)
                cubicTo(26.0269F, 51.8546F, 58.046F, 19.8354F, 98.886F, 6.56572F)
                cubicTo(119.093F, 0F, 144.062F, 0F, 194F, 0F)
                lineTo(679F, 0F)
                cubicTo(728.938F, 0F, 753.907F, 0F, 774.114F, 6.56572F)
                cubicTo(814.954F, 19.8354F, 846.973F, 51.8546F, 860.243F, 92.6946F)
                cubicTo(866.808F, 112.902F, 866.808F, 137.871F, 866.808F, 187.809F)
                lineTo(866.808F, 1582.96F)
                cubicTo(866.808F, 1632.9F, 866.808F, 1657.86F, 860.243F, 1678.07F)
                cubicTo(846.973F, 1718.91F, 814.954F, 1750.93F, 774.114F, 1764.2F)
                cubicTo(753.907F, 1770.77F, 728.938F, 1770.77F, 679F, 1770.77F)
                lineTo(194F, 1770.77F)
                cubicTo(144.062F, 1770.77F, 119.093F, 1770.77F, 98.886F, 1764.2F)
                cubicTo(58.046F, 1750.93F, 26.0269F, 1718.91F, 12.7571F, 1678.07F)
                cubicTo(6.19141F, 1657.86F, 6.19141F, 1632.9F, 6.19141F, 1582.96F)
                lineTo(6.19141F, 187.809F)
                close()
            }, color = Color(0xff213744)
        )

        drawPath(
            path = Path().apply {
                moveTo(679.825F, 4.12755F)
                lineTo(193.174F, 4.12755F)
                cubicTo(143.844F, 4.12755F, 119.668F, 4.15301F, 100.161F, 10.4912F)
                cubicTo(60.5778F, 23.3527F, 29.5438F, 54.3866F, 16.6824F, 93.97F)
                cubicTo(10.3442F, 113.477F, 10.3187F, 137.653F, 10.3187F, 186.983F)
                lineTo(10.3187F, 1583.78F)
                cubicTo(10.3187F, 1633.11F, 10.3442F, 1657.29F, 16.6824F, 1676.8F)
                cubicTo(29.5438F, 1716.38F, 60.5778F, 1747.41F, 100.161F, 1760.27F)
                cubicTo(119.668F, 1766.61F, 143.844F, 1766.64F, 193.174F, 1766.64F)
                lineTo(679.825F, 1766.64F)
                cubicTo(729.155F, 1766.64F, 753.331F, 1766.61F, 772.838F, 1760.27F)
                cubicTo(812.421F, 1747.41F, 843.455F, 1716.38F, 856.317F, 1676.8F)
                cubicTo(862.655F, 1657.29F, 862.68F, 1633.11F, 862.68F, 1583.78F)
                lineTo(862.68F, 186.983F)
                cubicTo(862.68F, 137.653F, 862.655F, 113.477F, 856.317F, 93.97F)
                cubicTo(843.455F, 54.3866F, 812.421F, 23.3527F, 772.838F, 10.4912F)
                cubicTo(753.331F, 4.15301F, 729.155F, 4.12755F, 679.825F, 4.12755F)
                close()
                moveTo(14.7196F, 93.3323F)
                cubicTo(8.25488F, 113.229F, 8.25488F, 137.813F, 8.25488F, 186.983F)
                lineTo(8.25488F, 1583.78F)
                cubicTo(8.25488F, 1632.95F, 8.25488F, 1657.54F, 14.7196F, 1677.43F)
                cubicTo(27.7852F, 1717.65F, 59.3117F, 1749.17F, 99.5235F, 1762.24F)
                cubicTo(119.42F, 1768.7F, 144.005F, 1768.7F, 193.174F, 1768.7F)
                lineTo(679.825F, 1768.7F)
                cubicTo(728.995F, 1768.7F, 753.579F, 1768.7F, 773.476F, 1762.24F)
                cubicTo(813.687F, 1749.17F, 845.214F, 1717.65F, 858.28F, 1677.43F)
                cubicTo(864.744F, 1657.54F, 864.744F, 1632.95F, 864.744F, 1583.78F)
                lineTo(864.744F, 186.983F)
                cubicTo(864.744F, 137.813F, 864.744F, 113.229F, 858.28F, 93.3323F)
                cubicTo(845.214F, 53.1206F, 813.687F, 21.594F, 773.476F, 8.52843F)
                cubicTo(753.579F, 2.06372F, 728.995F, 2.06372F, 679.825F, 2.06372F)
                lineTo(193.174F, 2.06372F)
                cubicTo(144.005F, 2.06372F, 119.42F, 2.06372F, 99.5235F, 8.52843F)
                cubicTo(59.3117F, 21.594F, 27.7852F, 53.1206F, 14.7196F, 93.3323F)
                close()
            },
            color = Color(0xff8EADC1)
        )

        drawPath(
            path = Path().apply {
                moveTo(16.5107F, 183.681F)
                cubicTo(16.5107F, 137.584F, 16.5107F, 114.536F, 22.5714F, 95.8834F)
                cubicTo(34.8204F, 58.1849F, 64.3765F, 28.6287F, 102.075F, 16.3798F)
                cubicTo(120.728F, 10.3191F, 143.776F, 10.3191F, 189.872F, 10.3191F)
                lineTo(683.128F, 10.3191F)
                cubicTo(729.224F, 10.3191F, 752.272F, 10.3191F, 770.925F, 16.3798F)
                cubicTo(808.624F, 28.6287F, 838.18F, 58.1849F, 850.429F, 95.8834F)
                cubicTo(856.49F, 114.536F, 856.49F, 137.584F, 856.49F, 183.681F)
                lineTo(856.49F, 1587.09F)
                cubicTo(856.49F, 1633.18F, 856.49F, 1656.23F, 850.429F, 1674.88F)
                cubicTo(838.18F, 1712.58F, 808.624F, 1742.14F, 770.925F, 1754.39F)
                cubicTo(752.272F, 1760.45F, 729.224F, 1760.45F, 683.128F, 1760.45F)
                lineTo(189.872F, 1760.45F)
                cubicTo(143.776F, 1760.45F, 120.728F, 1760.45F, 102.075F, 1754.39F)
                cubicTo(64.3765F, 1742.14F, 34.8204F, 1712.58F, 22.5714F, 1674.88F)
                cubicTo(16.5107F, 1656.23F, 16.5107F, 1633.18F, 16.5107F, 1587.09F)
                lineTo(16.5107F, 183.681F)
                close()
            },
            color = Color(0xff121515)
        )

        drawRectFromRect(
            Rect(
                left = size.width * 0.7825063F,
                top = 0F,
                right = size.width * 0.01418442F,
                bottom = size.height * 0.005826708F
            ),
            color = Color(0xff36454C)
        )

        drawRectFromRect(
            Rect(
                left = size.width * 0.9810871F,
                top = size.height * 0.1002196F,
                right = size.width * 0.01182027F,
                bottom = size.height * 0.006992095F
            ),
            color = Color(0xff36454C)
        )

        drawRectFromRect(
            Rect(
                left = size.width * 0.007092108F,
                top = size.height * 0.1002196F,
                right = size.width * 0.01182027F,
                bottom = size.height * 0.006992095F
            ),
            color = Color(0xff36454C)
        )

        drawRectFromRect(
            Rect(
                left = size.width * 0.007092108F,
                top = size.height * 0.8926539F,
                right = size.width * 0.01182027F,
                bottom = size.height * 0.006992095F
            ),
            color = Color(0xff36454C)
        )

        drawRectFromRect(
            Rect(
                left = size.width * 0.9810871F,
                top = size.height * 0.8926539F,
                right = size.width * 0.01182027F,
                bottom = size.height * 0.006992095F
            ),
            color = Color(0xff36454C)
        )

        drawRectFromRect(
            Rect(
                left = size.width * 0.2033093F,
                top = size.height * 0.9940429F,
                right = size.width * 0.01418442F,
                bottom = size.height * 0.005826708F
            ),
            color = Color(0xff36454C)
        )

        drawPath(
            path = Path().apply {
                moveTo(524.213F, 82.5533F)
                cubicTo(533.331F, 82.5533F, 540.723F, 75.1612F, 540.723F, 66.0426F)
                cubicTo(540.723F, 56.924F, 533.331F, 49.532F, 524.213F, 49.532F)
                cubicTo(515.094F, 49.532F, 507.702F, 56.924F, 507.702F, 66.0426F)
                cubicTo(507.702F, 75.1612F, 515.094F, 82.5533F, 524.213F, 82.5533F)
                close()
            },
            color = Color(0xff262C2D)
        )

        drawPath(
            path = Path().apply {
                moveTo(524.213F, 76.3617F)
                cubicTo(529.912F, 76.3617F, 534.532F, 71.7416F, 534.532F, 66.0425F)
                cubicTo(534.532F, 60.3434F, 529.912F, 55.7234F, 524.213F, 55.7234F)
                cubicTo(518.514F, 55.7234F, 513.894F, 60.3434F, 513.894F, 66.0425F)
                cubicTo(513.894F, 71.7416F, 518.514F, 76.3617F, 524.213F, 76.3617F)
                close()
            },
            color = Color(0xff121515)
        )

        drawPath(
            path = Path().apply {
                moveTo(524.213F, 63.9787F)
                cubicTo(525.353F, 63.9787F, 526.277F, 63.0547F, 526.277F, 61.9149F)
                cubicTo(526.277F, 60.7751F, 525.353F, 59.8511F, 524.213F, 59.8511F)
                cubicTo(523.073F, 59.8511F, 522.149F, 60.7751F, 522.149F, 61.9149F)
                cubicTo(522.149F, 63.0547F, 523.073F, 63.9787F, 524.213F, 63.9787F)
                close()
            },
            color = Color(0xff636F73)
        )

        drawPath(
            path = Path().apply {
                moveTo(385.937F, 66.0426F)
                cubicTo(385.937F, 62.6231F, 388.709F, 59.8511F, 392.128F, 59.8511F)
                lineTo(480.873F, 59.8511F)
                cubicTo(484.292F, 59.8511F, 487.064F, 62.6231F, 487.064F, 66.0426F)
                lineTo(487.064F, 66.0426F)
                cubicTo(487.064F, 69.462F, 484.292F, 72.2341F, 480.873F, 72.2341F)
                lineTo(392.128F, 72.2341F)
                cubicTo(388.709F, 72.2341F, 385.937F, 69.462F, 385.937F, 66.0426F)
                lineTo(385.937F, 66.0426F)
                close()
            },
            color = Color(0xff262C2D)
        )
    }
}

private val iPhone12Screen: Path.() -> Unit = {
    moveTo(224.958F, 50.564F)
    lineTo(224.958F, 59.8513F)
    lineTo(225.106F, 59.8466F)
    lineTo(224.958F, 60.8831F)
    cubicTo(224.958F, 85.3893F, 244.824F, 105.255F, 269.33F, 105.255F)
    lineTo(603.67F, 105.255F)
    cubicTo(628.176F, 105.255F, 648.043F, 85.3893F, 648.043F, 60.8831F)
    lineTo(647.895F, 59.8466F)
    lineTo(648.042F, 59.8513F)
    lineTo(648.042F, 50.564F)
    cubicTo(648.042F, 44.295F, 653.124F, 39.213F, 659.393F, 39.213F)
    lineTo(685.976F, 39.2129F)
    cubicTo(727.533F, 39.2129F, 748.311F, 39.2129F, 764.883F, 45.4748F)
    cubicTo(790.93F, 55.3172F, 811.492F, 75.8786F, 821.334F, 101.926F)
    cubicTo(827.596F, 118.498F, 827.596F, 139.276F, 827.596F, 180.833F)
    lineTo(827.596F, 1589.93F)
    cubicTo(827.596F, 1631.49F, 827.596F, 1652.27F, 821.334F, 1668.84F)
    cubicTo(811.492F, 1694.89F, 790.93F, 1715.45F, 764.883F, 1725.29F)
    cubicTo(748.311F, 1731.55F, 727.533F, 1731.55F, 685.976F, 1731.55F)
    lineTo(187.024F, 1731.55F)
    cubicTo(145.467F, 1731.55F, 124.689F, 1731.55F, 108.117F, 1725.29F)
    cubicTo(82.07F, 1715.45F, 61.5086F, 1694.89F, 51.6662F, 1668.84F)
    cubicTo(45.4043F, 1652.27F, 45.4043F, 1631.49F, 45.4043F, 1589.93F)
    lineTo(45.4043F, 180.833F)
    cubicTo(45.4043F, 139.276F, 45.4043F, 118.498F, 51.6662F, 101.926F)
    cubicTo(61.5086F, 75.8786F, 82.07F, 55.3172F, 108.117F, 45.4748F)
    cubicTo(124.689F, 39.2129F, 145.467F, 39.2129F, 187.024F, 39.2129F)
    lineTo(213.607F, 39.213F)
    cubicTo(219.876F, 39.213F, 224.958F, 44.295F, 224.958F, 50.564F)
    close()

    fillType = PathFillType.EvenOdd
}

internal val iphone12 = Device(
    name = "iPhone 12",
    resolution = Resolution(
        nativeSize = DeviceSize(
            width = 873,
            height = 1771
        )
    ),
    frame = iPhone12Frame,
    screenPath = iPhone12Screen,
    platform = DevicePlatform.IOS,
    safeArea = PaddingValues(vertical = 24.dp),
    rotatedSafeArea = PaddingValues(top = 24.dp, bottom = 20.dp)
)

