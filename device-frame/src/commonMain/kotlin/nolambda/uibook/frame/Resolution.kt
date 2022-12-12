package nolambda.uibook.frame

/**
 * Resolution of a device.
 * It's device size with a scale factor
 */
data class Resolution(
    val screenSize: DeviceSize,
    val frameSize: DeviceSize = screenSize,
    val scaleFactor: Float = 1f,
) {
    val logicalScreenSize = DeviceSize(
        width = (screenSize.width * scaleFactor).toInt(),
        height = (screenSize.height * scaleFactor).toInt()
    )

    val logical = DeviceSize(
        width = (frameSize.width * scaleFactor).toInt(),
        height = (frameSize.height * scaleFactor).toInt()
    )
}
