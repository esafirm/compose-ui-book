package nolambda.uibook.browser.config

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import java.util.UUID

interface ResourceLoader {
    @Composable
    fun load(id: ResourceId): Painter
}

sealed class ResourceId
class StringResourceId(val id: String = UUID.randomUUID().toString()) : ResourceId()

object ResourceIds {
    val MEASUREMENT_ENABLED = StringResourceId()
    val MEASUREMENT_DISABLED = StringResourceId()
}