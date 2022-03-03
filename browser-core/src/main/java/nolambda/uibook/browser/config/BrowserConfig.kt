package nolambda.uibook.browser.config

import nolambda.uibook.browser.BookHost

interface BrowserConfig {
    val bookHost: BookHost
    val resourceLoader: ResourceLoader
}

object AppBrowserConfig : BrowserConfig {

    private lateinit var internalConfig: BrowserConfig

    fun setConfig(passedConfig: BrowserConfig) {
        internalConfig = passedConfig
    }

    override val bookHost: BookHost
        get() = internalConfig.bookHost

    override val resourceLoader: ResourceLoader
        get() = internalConfig.resourceLoader
}