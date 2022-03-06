package nolambda.uibook.sample.component

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberImagePainter

@Suppress("EXPERIMENTAL_API_USAGE")
@Composable
actual fun RemoteImage(
    url: String,
    contentDescription: String?,
    modifier: Modifier,
    contentScale: ContentScale
) {
    Image(
        painter = rememberImagePainter(url),
        contentDescription = "avatar",
        contentScale = contentScale,
        modifier = modifier
    )
}