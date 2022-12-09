package nolambda.uibook.frame

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
        iPadPro
    )

    val nexus5 = Device(
        name = "Nexus 5",
        resolution = Resolution(
            screenSize = DeviceSize(
                width = 360,
                height = 640
            )
        ),
        platform = DevicePlatform.ANDROID
    )
    val nexus7 = Device(
        name = "Nexus 7",
        resolution = Resolution(
            screenSize = DeviceSize(
                width = 600,
                height = 960
            )
        ),
        platform = DevicePlatform.ANDROID
    )
    val nexus10 = Device(
        name = "Nexus 10",
        resolution = Resolution(
            screenSize = DeviceSize(
                width = 800,
                height = 1280
            )
        ),
        platform = DevicePlatform.ANDROID
    )
    val nexus9 = Device(
        name = "Nexus 9",
        resolution = Resolution(
            screenSize = DeviceSize(
                width = 768,
                height = 1024
            )
        ),
        platform = DevicePlatform.ANDROID
    )
    val nexus6 = Device(
        name = "Nexus 6",
        resolution = Resolution(
            screenSize = DeviceSize(
                width = 412,
                height = 732
            )
        ),
        platform = DevicePlatform.ANDROID
    )
}
