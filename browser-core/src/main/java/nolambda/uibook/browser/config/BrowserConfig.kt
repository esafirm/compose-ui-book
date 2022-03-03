package nolambda.uibook.browser.config

import nolambda.uibook.browser.BookHost

interface BrowserConfig {
    val bookHost: BookHost
}

object AppBrowserConfig : BrowserConfig {

    private lateinit var internalConfig: BrowserConfig

    fun setConfig(passedConfig: BrowserConfig) {
        internalConfig = passedConfig
    }

    override val bookHost: BookHost
        get() = internalConfig.bookHost
}