package nolambda.uibook.frame

/**
 * Resolution of a device.
 * It's device size with a scale factor
 */
data class Resolution(
    val nativeSize: DeviceSize,
    val scaleFactor: Float = 1f
) {
    val logicalDevice = DeviceSize(
        width = (nativeSize.width * scaleFactor).toInt(),
        height = (nativeSize.height * scaleFactor).toInt()
    )
}
