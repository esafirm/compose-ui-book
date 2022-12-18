package nolambda.uibook.browser.config

import nolambda.uibook.browser.BookHost
import nolambda.uibook.clipboard.ClipboardManager
import nolambda.uibook.factory.LibraryLoader

interface BrowserConfig {
    val bookHost: BookHost
    val resourceLoader: ResourceLoader
    val clipboardManager: ClipboardManager
    val libraryLoader: LibraryLoader
    val browserFeatures: Features

    data class Features(
        val measurementOverlay: Boolean = false,
    )
}

/**
 * Singleton delegate for [BrowserConfig].
 *
 * This is used to provide the infra for the browser and should be initialized before using the browser.
 */
object AppBrowserConfig : BrowserConfig {

    private lateinit var internalConfig: BrowserConfig

    fun setConfig(passedConfig: BrowserConfig) {
        internalConfig = passedConfig
    }

    override val bookHost: BookHost
        get() = internalConfig.bookHost

    override val resourceLoader: ResourceLoader
        get() = internalConfig.resourceLoader

    override val clipboardManager: ClipboardManager
        get() = internalConfig.clipboardManager

    override val libraryLoader: LibraryLoader
        get() = internalConfig.libraryLoader

    override val browserFeatures: BrowserConfig.Features
        get() = internalConfig.browserFeatures
}
