package nolambda.uibook.browser.config

actual fun randomResourceId(): String {
    return (kotlinx.browser.window.performance.now() * 1_000_000).toString()
}
