package nolambda.uibook.clipboard

import java.awt.Toolkit
import java.awt.datatransfer.StringSelection

/**
 * Implementation of [ClipboardManager] for desktop
 */
class DesktopClipboardManager(
    private val toolkit: Toolkit = Toolkit.getDefaultToolkit()
) : ClipboardManager {
    override fun copyToClipboard(text: String) {
        val clipboard = toolkit.systemClipboard
        val selection = StringSelection(text)
        clipboard.setContents(selection, selection)
    }
}
