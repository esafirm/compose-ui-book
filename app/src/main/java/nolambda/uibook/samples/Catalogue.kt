package nolambda.uibook.samples

import android.content.Context
import android.widget.TextView
import nolambda.uibook.annotations.UIBook

@UIBook(name = "TextView")
fun Context.createTextView(text: String): TextView {
    return TextView(this).apply {
        this.text = text
    }
}