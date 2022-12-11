package nolambda.uibook.frame

import nolambda.uibook.frame.android.onePlus8Pro
import nolambda.uibook.frame.ios.iPad
import nolambda.uibook.frame.ios.iPadPro
import nolambda.uibook.frame.ios.iPhoneSE
import nolambda.uibook.frame.ios.iPhone12

object Devices {
    val responsive = Device(
        name = "Responsive",
        resolution = Resolution(
            DeviceSize(width = 0, height = 0)
        ),
        platform = DevicePlatform.UNSPECIFIED
    )

    val all = listOf(
        responsive,
        iPhone12,
        iPhoneSE,
        iPad,
        iPadPro,
        onePlus8Pro
    )
}
