package nolambda.uibook.samples

import android.content.Context
import android.graphics.Color
import android.text.InputType
import android.view.View
import android.widget.EditText
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

@UIBook(name = "Input")
fun Context.createInput(input: String, hint: String, isNumber: Boolean): View {
    return EditText(this).apply {
        setText(input)
        setHint(hint)

        if (isNumber) {
            inputType = InputType.TYPE_CLASS_NUMBER
        }
    }
}