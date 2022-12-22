package nolambda.uibook.browser.app

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.window.Window
import androidx.compose.ui.zIndex
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import nolambda.uibook.browser.BookHost
import nolambda.uibook.browser.EmptyBookHost
import nolambda.uibook.browser.config.AppBrowserConfig
import nolambda.uibook.browser.config.BrowserConfig
import nolambda.uibook.browser.config.JsSettingStoreFactory
import nolambda.uibook.browser.config.ResourceLoader
import nolambda.uibook.browser.config.Setting
import nolambda.uibook.browser.config.SettingStore
import nolambda.uibook.browser.form.ComposeEmitter
import nolambda.uibook.clipboard.JsClipboardManager
import nolambda.uibook.clipboard.ClipboardManager
import nolambda.uibook.components.bookform.GlobalState
import nolambda.uibook.factory.BookConfig
import nolambda.uibook.factory.BookFactory
import nolambda.uibook.factory.JsLibraryLoader
import nolambda.uibook.factory.LibraryLoader
import nolambda.uibook.factory.UIBookLibrary
import org.jetbrains.skiko.wasm.onWasmReady

fun main() {
    runBrowser()
}

fun runBrowser() {
    // Setup the app config
    AppBrowserConfig.setConfig(object : BrowserConfig {

        override val bookHost: BookHost by lazy { EmptyBookHost() }
        override val resourceLoader: ResourceLoader by lazy { JsResourceLoader() }
        override val clipboardManager: ClipboardManager by lazy { JsClipboardManager() }
        override val libraryLoader: LibraryLoader by lazy {
            object : LibraryLoader {
                override fun load(): UIBookLibrary {
                    TODO()
                }
            }
        }

        override val browserFeatures: BrowserConfig.Features by lazy {
            BrowserConfig.Features(
                measurementOverlay = false
            )
        }
        override val settingStore: SettingStore by lazy {
            object : SettingStore {

                override suspend fun <T : Any> put(setting: Setting<T>, value: T) {
                }

                override suspend fun <T : Any> get(setting: Setting<T>): T {
                    return setting.defaultValue
                }

                override fun <T : Any> observe(scope: CoroutineScope, setting: Setting<T>): Flow<T> {
                    return flowOf(setting.defaultValue)
                }
            }
        }
    })

    onWasmReady {
        Window("Compose UIBook") {

            val library = AppBrowserConfig.libraryLoader.load()
            val factories = library.getBookFactories()
            val names = factories.map { it.getMetaData().name }

            val emptyBookConfig = object : BookConfig {
                override val onExit: () -> Unit = {}
            }

            MaterialTheme {

                var selectedIndex by remember { mutableStateOf(-1) }
                val factory = if (selectedIndex >= 0) factories[selectedIndex] else null
                val book = factory?.getBook(emptyBookConfig)

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

            }
        }
    }
}

@Composable
private fun BookList(
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
private fun BookViewer(
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
