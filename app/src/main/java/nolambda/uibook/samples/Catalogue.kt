package nolambda.uibook.samples

import android.content.Context
import android.graphics.Color
import android.widget.TextView
import nolambda.uibook.annotations.UIBook

@UIBook(name = "TextView")
fun Context.createTextView(text: String): TextView {
    /**
     * This will draw text
     */
    return TextView(this).apply {
        this.text = text
        setTextColor(Color.RED)
    }
}