package nolambda.uibook.browser.config

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter

interface ResourceLoader {
    @Composable
    fun load(id: ResourceId): Painter
}

sealed class ResourceId
class StringResourceId(val id: String = randomResourceId()) : ResourceId()

expect fun randomResourceId(): String

object ResourceIds {
    val MEASUREMENT_ENABLED = StringResourceId()
    val MEASUREMENT_DISABLED = StringResourceId()
}
