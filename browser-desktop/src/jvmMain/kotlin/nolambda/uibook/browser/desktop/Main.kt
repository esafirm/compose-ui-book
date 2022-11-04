package nolambda.uibook.browser.desktop

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import nolambda.uibook.browser.BookHost
import nolambda.uibook.browser.EmptyBookHost
import nolambda.uibook.browser.config.AppBrowserConfig
import nolambda.uibook.browser.config.BrowserConfig
import nolambda.uibook.browser.config.ResourceLoader
import nolambda.uibook.components.booklist.BookList
import nolambda.uibook.factory.BookConfig
import nolambda.uibook.factory.LibraryLoader

val library = LibraryLoader.load()
val names = library.getBookFactories().map { it.getMetaData().name }

fun main() {
    runBrowser()
}

fun runBrowser() {
    AppBrowserConfig.setConfig(object : BrowserConfig {
        override val bookHost: BookHost by lazy {
            EmptyBookHost()
        }
        override val resourceLoader: ResourceLoader by lazy {
            DesktopResourceLoader()
        }
    })

    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Android UI Book - Browser",
            state = rememberWindowState(
                position = WindowPosition(alignment = Alignment.Center),
            ),
        ) {
            MaterialTheme {
                var selectedIndex by remember { mutableStateOf(-1) }
                Row {
                    ComponentList(names) { index ->
                        selectedIndex = index
                    }
                    ComponentViewer(selectedIndex)
                }
            }
        }
    }
}

@Composable
private fun RowScope.ComponentList(
    names: List<String>,
    onSelected: (index: Int) -> Unit
) {
    Box(modifier = Modifier.weight(1F)) {
        BookList(bookNames = names, onSelected)
    }
}

@Composable
private fun RowScope.ComponentViewer(
    selectedIndex: Int
) {
    val emptyBookConfig = object : BookConfig {
        override val onExit: () -> Unit = {}
    }

    Box(
        modifier = Modifier.weight(3F)
    ) {

        val isSelected = selectedIndex >= 0
        if (!isSelected) {
            EmptyContent()
        } else {
            val factory = remember(selectedIndex) { library.getBookFactories()[selectedIndex] }
            factory.getBook(emptyBookConfig).invoke()
        }
    }
}