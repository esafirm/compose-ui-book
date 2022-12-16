package nolambda.uibook.browser.desktop

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import nolambda.uibook.browser.config.ResourceId
import nolambda.uibook.browser.config.ResourceIds
import nolambda.uibook.browser.config.ResourceLoader

class DesktopResourceLoader : ResourceLoader {
    @Composable
    override fun load(id: ResourceId): Painter {
        val resource = mapIdToResource(id)
        if (resource != null) {
            return painterResource(resource)
        }
        error("There's not registered loader for $id")
    }

    private fun mapIdToResource(id: ResourceId): String? {
        return when (id) {
            ResourceIds.MEASUREMENT_ENABLED -> "images/ic_measurement_enabled.xml"
            ResourceIds.MEASUREMENT_DISABLED -> "images/ic_measurement_disabled.xml"
            ResourceIds.DEVICE_FRAME -> "images/ic_device.xml"
            else -> null
        }
    }
}
