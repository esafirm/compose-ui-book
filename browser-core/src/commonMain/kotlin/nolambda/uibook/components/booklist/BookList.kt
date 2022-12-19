package nolambda.uibook.components.booklist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import nolambda.uibook.components.common.ClickableText

@Composable
fun BookList(
    bookNames: List<String>,
    modifier: Modifier = Modifier,
    navigateToBook: (index: Int) -> Unit,
    onSettingClick: () -> Unit,
) {
    Column(modifier = modifier) {
        TopAppBar {
            Text(
                text = "UIBook",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp)
                    .weight(1F)
            )
            IconButton(
                onClick = onSettingClick,
                modifier = Modifier.padding(end = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More Settings",
                    tint = Color.White
                )
            }
        }
        LazyColumn {
            itemsIndexed(bookNames) { index, name ->
                ClickableText(text = name, modifier = Modifier.padding(16.dp)) {
                    navigateToBook(index)
                }
            }
        }
    }
}
