package nolambda.uibook.icons

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector

internal val UIBookIcons.FullscreenExit: ImageVector
    get() {
        if (_fullscreenExit != null) {
            return _fullscreenExit!!
        }
        _fullscreenExit = materialIcon(name = "Filled.FullscreenExit") {
            materialPath {
                moveTo(5.0f, 16.0f)
                horizontalLineToRelative(3.0f)
                verticalLineToRelative(3.0f)
                horizontalLineToRelative(2.0f)
                verticalLineToRelative(-5.0f)
                lineTo(5.0f, 14.0f)
                verticalLineToRelative(2.0f)
                close()
                moveTo(8.0f, 8.0f)
                lineTo(5.0f, 8.0f)
                verticalLineToRelative(2.0f)
                horizontalLineToRelative(5.0f)
                lineTo(10.0f, 5.0f)
                lineTo(8.0f, 5.0f)
                verticalLineToRelative(3.0f)
                close()
                moveTo(14.0f, 19.0f)
                horizontalLineToRelative(2.0f)
                verticalLineToRelative(-3.0f)
                horizontalLineToRelative(3.0f)
                verticalLineToRelative(-2.0f)
                horizontalLineToRelative(-5.0f)
                verticalLineToRelative(5.0f)
                close()
                moveTo(16.0f, 8.0f)
                lineTo(16.0f, 5.0f)
                horizontalLineToRelative(-2.0f)
                verticalLineToRelative(5.0f)
                horizontalLineToRelative(5.0f)
                lineTo(19.0f, 8.0f)
                horizontalLineToRelative(-3.0f)
                close()
            }
        }
        return _fullscreenExit!!
    }

private var _fullscreenExit: ImageVector? = null
