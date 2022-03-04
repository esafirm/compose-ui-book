package nolambda.uibook.browser.desktop

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BoxScope.EmptyContent() {
    Text(
        text = "No selected component",
        modifier = Modifier.size(22.dp)
            .align(alignment = Alignment.Center)
    )
}