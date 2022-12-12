package nolambda.uibook.frame

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp

data class Device(
    val name: String,
    val resolution: Resolution,
    val platform: DevicePlatform,
    val frame: @Composable (Modifier) -> Unit = {},
    val screenPath: Path.() -> Unit = {},
    val safeArea: PaddingValues = PaddingValues(all = 0.dp),
    val rotatedSafeArea: PaddingValues = PaddingValues(all = 0.dp)
)
