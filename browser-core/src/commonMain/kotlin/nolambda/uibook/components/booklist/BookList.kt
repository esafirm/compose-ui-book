package nolambda.uibook.components.booklist

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun BookList(
    bookNames: List<String>,
    modifier: Modifier = Modifier,
    navigateToBook: (index: Int) -> Unit,
) {
    Column(modifier = modifier) {
        TopAppBar {
            Text(
                text = "UIBook",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
        LazyColumn {
            itemsIndexed(bookNames) { index, name ->
                ClickableText(text = name) {
                    navigateToBook(index)
                }
            }
        }
    }
}

@Composable
private fun ClickableText(
    text: String,
    textColor: Color = Color.Black,
    onClick: () -> Unit,
) {
    Text(
        text = text, color = textColor, modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(),
                onClick = onClick
            )
            .padding(16.dp)
    )
}
