package nolambda.uibook.frame

object Devices {
    val responsive = Device(
        name = "Responsive",
        resolution = Resolution(
            DeviceSize(width = 0, height = 0)
        )
    )

    val iPhoneX = Device(
        name = "iPhone X",
        resolution = Resolution(
            nativeSize = DeviceSize(
                width = 375,
                height = 812
            )
        )
    )
    val iPhone8 = Device(
        name = "iPhone 8",
        resolution = Resolution(
            nativeSize = DeviceSize(
                width = 375,
                height = 667
            )
        )
    )
    val iPhoneSE = Device(
        name = "iPhone SE",
        resolution = Resolution(
            nativeSize = DeviceSize(
                width = 320,
                height = 568
            )
        )
    )
    val iPadPro = Device(
        name = "iPad Pro",
        resolution = Resolution(
            nativeSize = DeviceSize(
                width = 1024,
                height = 1366
            )
        )
    )
    val iPad = Device(
        name = "iPad",
        resolution = Resolution(
            nativeSize = DeviceSize(
                width = 768,
                height = 1024
            )
        )
    )
    val nexus5 = Device(
        name = "Nexus 5",
        resolution = Resolution(
            nativeSize = DeviceSize(
                width = 360,
                height = 640
            )
        )
    )
    val nexus7 = Device(
        name = "Nexus 7",
        resolution = Resolution(
            nativeSize = DeviceSize(
                width = 600,
                height = 960
            )
        )
    )
    val nexus10 = Device(
        name = "Nexus 10",
        resolution = Resolution(
            nativeSize = DeviceSize(
                width = 800,
                height = 1280
            )
        )
    )
    val nexus9 = Device(
        name = "Nexus 9",
        resolution = Resolution(
            nativeSize = DeviceSize(
                width = 768,
                height = 1024
            )
        )
    )
    val nexus6 = Device(
        name = "Nexus 6",
        resolution = Resolution(
            nativeSize = DeviceSize(
                width = 412,
                height = 732
            )
        )
    )

    val all = listOf(
        responsive,
        iPhoneX,
        iPhone8,
        iPhoneSE,
        iPadPro,
        iPad,
        nexus5,
        nexus7,
        nexus10,
        nexus9,
        nexus6
    )
}
