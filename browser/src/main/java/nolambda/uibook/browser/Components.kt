package nolambda.uibook.browser

import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.wakaztahir.codeeditor.highlight.model.CodeLang
import com.wakaztahir.codeeditor.highlight.prettify.PrettifyParser
import com.wakaztahir.codeeditor.highlight.theme.CodeThemeType
import com.wakaztahir.codeeditor.highlight.utils.parseCodeAsAnnotatedString
import nolambda.uibook.annotations.BookMetaData

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
                ClickableText(text = name) {
                    UIBookActivity.start(context, index)
                }
            }
        }
    }
}

@Composable
private fun ClickableText(
    text: String,
    textColor: Color = Color.Black,
    onClick: () -> Unit
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

@Composable
fun BookForm(
    metaData: BookMetaData,
    view: View
) {
    val selectedIndex = remember { mutableStateOf(0) }
    Column {
        TopAppBar {
            Row {
                Text(text = metaData.name, fontSize = 18.sp, modifier = Modifier.padding(16.dp))
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F)
        ) {
            AndroidView(factory = { view }, modifier = Modifier.align(Alignment.Center))
        }

        Column(modifier = Modifier.weight(1F)) {
            TabView(
                titles = listOf("Source Code".uppercase(), "Modifier".uppercase()),
                selectedIndex = selectedIndex.value
            ) {
                selectedIndex.value = it
            }

            if (selectedIndex.value == 0) {
                SourceCodeView(rawSourceCode = metaData.function)
            } else {
                Text(text = "Modifier view")
            }
        }
    }
}


@Composable
fun SourceCodeView(
    rawSourceCode: String
) {
    val verticalScrollState = rememberScrollState()
    val horizontalScrollState = rememberScrollState()

    val functionCode = remember {
        rawSourceCode.replace("return ", "")
            .removeSurrounding("{", "}")
            .trimIndent()
    }

    val parser = remember { PrettifyParser() }
    val theme = remember { CodeThemeType.Monokai.theme() }

    val parsedCode = remember {
        parseCodeAsAnnotatedString(
            parser = parser,
            theme = theme,
            lang = CodeLang.Java,
            code = functionCode
        )
    }

    Text(
        parsedCode, modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.monokai_background))
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)
            .verticalScroll(verticalScrollState)
            .horizontalScroll(horizontalScrollState)
    )
}

@Composable
fun TabView(
    titles: List<String>,
    selectedIndex: Int = 0,
    onClick: (Int) -> Unit,
) {
    Row {
        titles.forEachIndexed { index, title ->

            val isSelected = selectedIndex == index
            val textColor = colorResource(id = if (isSelected) R.color.purple_700 else R.color.black)

            Box(modifier = Modifier.weight(1F)) {
                Text(
                    text = title,
                    color = textColor,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .rippleClick { onClick(index) }
                        .padding(16.dp)
                )

                if (isSelected) {
                    Box(
                        modifier = Modifier
                            .align(alignment = Alignment.BottomCenter)
                            .fillMaxWidth()
                            .height(2.dp)
                            .clip(RectangleShape)
                            .background(textColor)
                    )
                }
            }
        }
    }
}

@Composable
private fun Modifier.rippleClick(onClick: () -> Unit): Modifier {
    return clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = rememberRipple(),
        onClick = onClick
    )
}

