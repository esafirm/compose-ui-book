package nolambda.uibook.clipboard

import android.content.Context
import android.content.ClipboardManager as AndroidClipboardManager

class AndroidClipboardManager(
    private val context: Context,
    private val label: String = DEFAULT_LABEL
) : ClipboardManager {

    companion object {
        private const val DEFAULT_LABEL = "UIBook.Cb"
    }

    override fun copyToClipboard(text: String) {
        val clipboard =
            context.getSystemService(Context.CLIPBOARD_SERVICE) as AndroidClipboardManager
        val clip = android.content.ClipData.newPlainText(label, text)
        clipboard.setPrimaryClip(clip)
    }
}
