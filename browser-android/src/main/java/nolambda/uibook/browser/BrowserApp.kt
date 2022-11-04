package nolambda.uibook.browser

import android.app.Application
import nolambda.uibook.browser.AndroidBookHost
import nolambda.uibook.browser.BookHost
import nolambda.uibook.browser.config.AppBrowserConfig
import nolambda.uibook.browser.config.BrowserConfig
import nolambda.uibook.browser.config.ResourceLoader
import nolambda.uibook.browser.resourceloader.AndroidResourceLoader

class BrowserApp : Application() {

    override fun onCreate() {
        super.onCreate()
        AppBrowserConfig.setConfig(object : BrowserConfig {
            override val bookHost: BookHost by lazy { AndroidBookHost(this@BrowserApp) }

            override val resourceLoader: ResourceLoader by lazy {
                AndroidResourceLoader()
            }
        })
    }
}