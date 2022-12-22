package nolambda.uibook.browser.app

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nolambda.uibook.browser.form.ComposeEmitter
import nolambda.uibook.setting.SettingPage
import nolambda.uibook.utils.simpleClick

@Composable
internal fun EmptyContent() {
    Row(
        modifier = Modifier.fillMaxSize()
            .background(Color.LightGray),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Outlined.Info, "info")

        Spacer(Modifier.size(8.dp))

        Text(
            text = "No selected component",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.W500,
            fontSize = 22.sp
        )
    }
}

@Composable
internal fun SettingModal(
    showSetting: Boolean,
    setShowSetting: (Boolean) -> Unit,
) {
    AnimatedVisibility(
        visible = showSetting,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.2f))
                .simpleClick { setShowSetting(false) }
                .padding(24.dp)
        ) {
            SettingPage(
                modifier = Modifier.clickable(false, onClick = {}),
            ) {
                setShowSetting(false)
            }
        }
    }
}

@Composable
internal fun BookList(
    names: List<String>,
    modifier: Modifier = Modifier,
    onSelected: (index: Int) -> Unit,
    onSettingClick: () -> Unit,
) {
    Box(modifier = modifier) {
        nolambda.uibook.components.booklist.BookList(
            bookNames = names,
            modifier = Modifier
                .fillMaxHeight()
                .background(MaterialTheme.colors.background),
            navigateToBook = onSelected,
            onSettingClick = onSettingClick
        )
    }
}

@Composable
internal fun BookViewer(
    modifier: Modifier = Modifier,
    book: ComposeEmitter? = null,
) {
    Box(
        modifier = modifier
    ) {
        if (book == null) {
            EmptyContent()
        } else {
            book()
        }
    }
}
