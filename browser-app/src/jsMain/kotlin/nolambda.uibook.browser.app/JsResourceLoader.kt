package nolambda.uibook.browser.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import nolambda.uibook.browser.config.ResourceId
import nolambda.uibook.browser.config.ResourceIds
import nolambda.uibook.browser.config.ResourceLoader
import nolambda.uibook.icons.ContentCopy
import nolambda.uibook.icons.UIBookIcons

class JsResourceLoader : ResourceLoader {
    @Composable
    override fun load(id: ResourceId): Painter {
        error("There's not registered loader for $id")
    }
}
