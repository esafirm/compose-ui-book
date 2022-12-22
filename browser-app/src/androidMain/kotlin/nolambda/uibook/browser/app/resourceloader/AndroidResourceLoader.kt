package nolambda.uibook.browser.app.resourceloader

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import nolambda.uibook.browser.app.R
import nolambda.uibook.browser.config.ResourceId
import nolambda.uibook.browser.config.ResourceIds
import nolambda.uibook.browser.config.ResourceLoader

class AndroidResourceLoader : ResourceLoader {
    @Composable
    override fun load(id: ResourceId): Painter {
        val resource = mapIdToResource(id)
        if (resource != null) {
            return painterResource(id = resource)
        }
        error("There's not registered loader for $id")
    }

    private fun mapIdToResource(id: ResourceId): Int? {
        return when (id) {
            ResourceIds.MEASUREMENT_ENABLED -> R.drawable.ic_measurement_enabled
            ResourceIds.MEASUREMENT_DISABLED -> R.drawable.ic_measurement_disabled
            else -> null
        }
    }
}
