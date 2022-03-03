package nolambda.uibook

import android.app.Application
import nolambda.uibook.browser.AndroidBookHost
import nolambda.uibook.browser.BookHost
import nolambda.uibook.browser.config.AppBrowserConfig
import nolambda.uibook.browser.config.BrowserConfig

class BrowserApp : Application() {

    override fun onCreate() {
        super.onCreate()
        AppBrowserConfig.setConfig(object : BrowserConfig {
            override val bookHost: BookHost
                get() = AndroidBookHost(this@BrowserApp)
        })
    }
}