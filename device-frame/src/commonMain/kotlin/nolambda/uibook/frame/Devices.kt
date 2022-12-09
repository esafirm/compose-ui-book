package nolambda.uibook.frame

import nolambda.uibook.frame.ios.iphone12

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
        iphone12,

    )

    val iPhone8 = Device(
        name = "iPhone 8",
        resolution = Resolution(
            nativeSize = DeviceSize(
                width = 375,
                height = 667
            )
        ),
        platform = DevicePlatform.IOS
    )
    val iPhoneSE = Device(
        name = "iPhone SE",
        resolution = Resolution(
            nativeSize = DeviceSize(
                width = 320,
                height = 568
            )
        ),
        platform = DevicePlatform.IOS
    )
    val iPadPro = Device(
        name = "iPad Pro",
        resolution = Resolution(
            nativeSize = DeviceSize(
                width = 1024,
                height = 1366
            )
        ),
        platform = DevicePlatform.IOS
    )
    val iPad = Device(
        name = "iPad",
        resolution = Resolution(
            nativeSize = DeviceSize(
                width = 768,
                height = 1024
            )
        ),
        platform = DevicePlatform.IOS
    )
    val nexus5 = Device(
        name = "Nexus 5",
        resolution = Resolution(
            nativeSize = DeviceSize(
                width = 360,
                height = 640
            )
        ),
        platform = DevicePlatform.ANDROID
    )
    val nexus7 = Device(
        name = "Nexus 7",
        resolution = Resolution(
            nativeSize = DeviceSize(
                width = 600,
                height = 960
            )
        ),
        platform = DevicePlatform.ANDROID
    )
    val nexus10 = Device(
        name = "Nexus 10",
        resolution = Resolution(
            nativeSize = DeviceSize(
                width = 800,
                height = 1280
            )
        ),
        platform = DevicePlatform.ANDROID
    )
    val nexus9 = Device(
        name = "Nexus 9",
        resolution = Resolution(
            nativeSize = DeviceSize(
                width = 768,
                height = 1024
            )
        ),
        platform = DevicePlatform.ANDROID
    )
    val nexus6 = Device(
        name = "Nexus 6",
        resolution = Resolution(
            nativeSize = DeviceSize(
                width = 412,
                height = 732
            )
        ),
        platform = DevicePlatform.ANDROID
    )
}
