package nolambda.uibook.browser.app

import android.app.Application
import nolambda.uibook.browser.AndroidBookHost
import nolambda.uibook.browser.BookHost
import nolambda.uibook.browser.config.AppBrowserConfig
import nolambda.uibook.browser.config.BrowserConfig
import nolambda.uibook.browser.config.ResourceLoader
import nolambda.uibook.browser.app.resourceloader.AndroidResourceLoader
import nolambda.uibook.clipboard.AndroidClipboardManager
import nolambda.uibook.clipboard.ClipboardManager
import nolambda.uibook.factory.AndroidLibraryLoader
import nolambda.uibook.factory.LibraryLoader

class BrowserApp : Application() {

    override fun onCreate() {
        super.onCreate()
        AppBrowserConfig.setConfig(object : BrowserConfig {
            override val bookHost: BookHost by lazy { AndroidBookHost(this@BrowserApp) }

            override val resourceLoader: ResourceLoader by lazy {
                AndroidResourceLoader()
            }

            override val clipboardManager: ClipboardManager by lazy {
                AndroidClipboardManager(this@BrowserApp)
            }

            override val libraryLoader: LibraryLoader by lazy {
                AndroidLibraryLoader()
            }
            override val browserFeatures: BrowserConfig.Features by lazy {
                BrowserConfig.Features(
                    measurementOverlay = true
                )
            }
        })
    }
}
