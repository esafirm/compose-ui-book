package nolambda.uibook.frame

import androidx.compose.ui.graphics.Path

/**
 * Size of a device. Will be used to represent the size of the device in a frame.
 */
data class DeviceSize(
    val width: Int,
    val height: Int,
) {
    companion object {

        fun fromPath(path: Path): DeviceSize {
            val bounds = path.getBounds()
            return DeviceSize(
                width = bounds.width.toInt(),
                height = bounds.height.toInt()
            )
        }

        fun fromPathFunc(pathFunc: Path.() -> Unit): DeviceSize {
            val path = Path().apply(pathFunc)
            return fromPath(path)
        }
    }
}
