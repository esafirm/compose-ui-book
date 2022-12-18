package nolambda.uibook.browser.app

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import androidx.compose.ui.zIndex
import nolambda.uibook.browser.BookHost
import nolambda.uibook.browser.EmptyBookHost
import nolambda.uibook.browser.config.AppBrowserConfig
import nolambda.uibook.browser.config.BrowserConfig
import nolambda.uibook.browser.config.ResourceLoader
import nolambda.uibook.clipboard.ClipboardManager
import nolambda.uibook.clipboard.DesktopClipboardManager
import nolambda.uibook.components.bookform.GlobalState
import nolambda.uibook.components.booklist.BookList
import nolambda.uibook.factory.BookConfig
import nolambda.uibook.factory.DesktopLibraryLoader
import nolambda.uibook.factory.LibraryLoader
import nolambda.uibook.factory.UIBookLibrary

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
        override val clipboardManager: ClipboardManager by lazy {
            DesktopClipboardManager()
        }

        override val libraryLoader: LibraryLoader by lazy {
            DesktopLibraryLoader()
        }
        override val browserFeatures: BrowserConfig.Features by lazy {
            BrowserConfig.Features(
                measurementOverlay = false
            )
        }
    })

    val library = AppBrowserConfig.libraryLoader.load()
    val names = library.getBookFactories().map { it.getMetaData().name }

    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Compose UI Book - Browser",
            state = rememberWindowState(
                size = DpSize(1440.dp, 600.dp),
                position = WindowPosition(alignment = Alignment.Center),
            ),
        ) {
            MaterialTheme {
                var selectedIndex by remember { mutableStateOf(-1) }
                Row {
                    val isFullScreen = GlobalState.fullScreenMode.value
                    AnimatedVisibility(
                        modifier = Modifier.weight(1F).zIndex(2f),
                        visible = isFullScreen.not(),
                        exit = slideOut { fullSize -> IntOffset(-fullSize.width, 0) },
                        enter = slideIn { fullSize -> IntOffset(-fullSize.width, 0) }
                    ) {
                        ComponentList(
                            names = names,
                        ) { index ->
                            selectedIndex = index
                        }
                    }

                    ComponentViewer(
                        selectedIndex = selectedIndex,
                        modifier = Modifier.weight(3F).zIndex(1f),
                        library = library
                    )
                }
            }
        }
    }
}

@Composable
private fun ComponentList(
    names: List<String>,
    modifier: Modifier = Modifier,
    onSelected: (index: Int) -> Unit,
) {
    Box(modifier = modifier) {
        BookList(
            bookNames = names,
            modifier = Modifier
                .fillMaxHeight()
                .background(MaterialTheme.colors.background),
            navigateToBook = onSelected
        )
    }
}

@Composable
private fun ComponentViewer(
    selectedIndex: Int,
    modifier: Modifier = Modifier,
    library: UIBookLibrary,
) {
    val emptyBookConfig = object : BookConfig {
        override val onExit: () -> Unit = {}
    }

    Box(
        modifier = modifier
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
