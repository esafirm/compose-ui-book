package nolambda.uibook.browser

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun BookList(bookNames: List<String>) {
    val context = LocalContext.current
    Column {
        TopAppBar {
            Text(
                text = "UIBook",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
        LazyColumn {
            itemsIndexed(bookNames) { index, name ->
                Text(
                    text = name, color = Color.Black, modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(),
                            onClick = { UIBookActivity.start(context, index) }
                        )
                        .padding(16.dp)
                )
            }
        }
    }
}

