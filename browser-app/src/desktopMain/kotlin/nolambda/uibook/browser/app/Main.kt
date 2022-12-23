package nolambda.uibook.browser.app

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
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
import nolambda.uibook.browser.config.DesktopSettingStoreFactory
import nolambda.uibook.browser.config.ResourceLoader
import nolambda.uibook.browser.config.SettingStore
import nolambda.uibook.clipboard.ClipboardManager
import nolambda.uibook.clipboard.DesktopClipboardManager
import nolambda.uibook.components.bookform.GlobalState
import nolambda.uibook.factory.BookConfig
import nolambda.uibook.factory.DesktopLibraryLoader
import nolambda.uibook.factory.LibraryLoader

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
        override val settingStore: SettingStore by lazy {
            DesktopSettingStoreFactory.createStore()
        }
    })

    val library = AppBrowserConfig.libraryLoader.load()
    val factories = library.getBookFactories()
    val names = factories.map { it.getMetaData().name }

    val emptyBookConfig = object : BookConfig {
        override val onExit: () -> Unit = {}
    }

    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Compose UI Book - Browser",
            state = rememberWindowState(
                size = DpSize(1440.dp, 600.dp),
                position = WindowPosition(alignment = Alignment.Center),
            ),
        ) {
            var selectedIndex by remember { mutableStateOf(-1) }
            val factory = if (selectedIndex >= 0) factories[selectedIndex] else null
            val book = factory?.getBook(emptyBookConfig)

            MaterialTheme {
                val (showSetting, setShowSetting) = remember { mutableStateOf(false) }

                Row {
                    val isFullScreen = GlobalState.fullScreenMode.value
                    AnimatedVisibility(
                        modifier = Modifier.weight(1F).zIndex(2f),
                        visible = isFullScreen.not(),
                        exit = slideOut { fullSize -> IntOffset(-fullSize.width, 0) },
                        enter = slideIn { fullSize -> IntOffset(-fullSize.width, 0) }
                    ) {
                        BookList(
                            names = names,
                            onSelected = { index -> selectedIndex = index },
                            onSettingClick = { setShowSetting(true) }
                        )
                    }

                    BookViewer(
                        modifier = Modifier.weight(3F).zIndex(1f),
                        book = book
                    )
                }

                SettingModal(showSetting, setShowSetting)
            }
        }
    }
}

