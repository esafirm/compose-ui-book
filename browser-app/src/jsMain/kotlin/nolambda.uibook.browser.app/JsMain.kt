package nolambda.uibook.browser.app

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.window.Window
import androidx.compose.ui.zIndex
import nolambda.uibook.browser.BookHost
import nolambda.uibook.browser.EmptyBookHost
import nolambda.uibook.browser.config.AppBrowserConfig
import nolambda.uibook.browser.config.BrowserConfig
import nolambda.uibook.browser.config.JsSettingStoreFactory
import nolambda.uibook.browser.config.ResourceLoader
import nolambda.uibook.browser.config.SettingStore
import nolambda.uibook.clipboard.ClipboardManager
import nolambda.uibook.clipboard.JsClipboardManager
import nolambda.uibook.components.bookform.DropdownMenuShower
import nolambda.uibook.components.bookform.GlobalState
import nolambda.uibook.components.bookform.LocalDropdownShow
import nolambda.uibook.factory.BookConfig
import nolambda.uibook.factory.LibraryLoader
import nolambda.uibook.factory.UIBookLibrary
import org.jetbrains.skiko.wasm.onWasmReady

fun runBrowser(library: UIBookLibrary) {
    // Setup the app config
    AppBrowserConfig.setConfig(object : BrowserConfig {

        override val bookHost: BookHost by lazy { EmptyBookHost() }
        override val resourceLoader: ResourceLoader by lazy { JsResourceLoader() }
        override val clipboardManager: ClipboardManager by lazy { JsClipboardManager() }

        // Because of limitation this is retrieved from the caller of [runBrowser] function
        override val libraryLoader: LibraryLoader = object : LibraryLoader {
            override fun load(): UIBookLibrary = library
        }

        override val browserFeatures: BrowserConfig.Features by lazy {
            BrowserConfig.Features(
                measurementOverlay = false
            )
        }

        override val settingStore: SettingStore by lazy {
            JsSettingStoreFactory.createStore()
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
                CompositionLocalProvider(LocalDropdownShow provides DropdownMenuShower()) {

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

                    SettingModal(
                        showSetting = showSetting,
                        setShowSetting = setShowSetting
                    )
                    DropdownRenderer()
                }
            }
        }
    }
}

/**
 * Hack to make dropdown menu rendered here
 *
 * @see LocalDropdownShow
 * @see DropdownMenuShower
 */
@Composable
fun DropdownRenderer() {
    val renderState = LocalDropdownShow.current.composable
    if (renderState.value.render) {
        renderState.value.content()
    }
}
