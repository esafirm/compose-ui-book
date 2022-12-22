package nolambda.uibook.clipboard

import kotlinx.browser.window

/**
 * Implementation of [ClipboardManager] for JS.
 */
class JsClipboardManager : ClipboardManager {
    override fun copyToClipboard(text: String) {
        window.navigator.clipboard.writeText(text)
    }
}
