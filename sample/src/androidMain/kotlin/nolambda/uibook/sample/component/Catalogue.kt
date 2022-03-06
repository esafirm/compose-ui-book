package nolambda.uibook.sample.component

import android.graphics.Typeface
import android.util.TypedValue
import android.view.View
import android.widget.EditText
import android.widget.TextView
import nolambda.uibook.annotations.State
import nolambda.uibook.annotations.UIBook
import nolambda.uibook.annotations.code.CodeSpec
import nolambda.uibook.browser.AndroidBookHost
import nolambda.uibook.databinding.ItemTextImageBinding
import nolambda.uibook.databinding.ItemWithBooleanBinding

@UIBook(name = "TextView")
fun AndroidBookHost.createTextView(text: String): TextView {
    /**
     * This will draw text
     */
    return TextView(context).apply {
        this.text = text
        setTextColor(android.graphics.Color.RED)
    }
}

@UIBook(
    name = "Custom Input",
    inputCreator = ButtonInput::class
)
fun AndroidBookHost.createTextViewCustom(text: String): TextView {
    return TextView(context).apply {
        this.text = text
    }
}

@UIBook(name = "Input")
fun AndroidBookHost.createInput(input: String, hint: String): View {
    return EditText(context).apply {
        setText(input)
        setHint(hint)
    }
}

@UIBook(name = "TextWithImage")
fun AndroidBookHost.createTextWithImage(title: String): View {
    val binding = ItemTextImageBinding.inflate(inflater, null, false)
    binding.txtTitle.text = title
    return binding.root
}

@UIBook(name = "This is a name")
fun AndroidBookHost.createBooleanText(isBold: Boolean): View {
    val binding = ItemWithBooleanBinding.inflate(inflater, null, false)
    binding.txtSubtitle.setTypeface(
        null, if (isBold) {
            Typeface.BOLD
        } else {
            Typeface.NORMAL
        }
    )
    return binding.root
}

@UIBook(name = "Custom code")
@CodeSpec(
    code = """
        <TextView android:text="testing"
                  android:textSize="17sp" />
    """,
    trimIndent = true,
    language = "xml"
)
fun AndroidBookHost.createCustomCodeComponent(
    text: String,
    @State(defaultValue = "17F") textSize: Float
): View {
    return TextView(context).apply {
        this.text = text.ifBlank { "testing" }
        this.textSize =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, textSize, context.resources.displayMetrics)
    }
}

