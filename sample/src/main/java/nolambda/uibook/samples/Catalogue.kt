package nolambda.uibook.samples

import android.graphics.Color
import android.graphics.Typeface
import android.view.View
import android.widget.EditText
import android.widget.TextView
import nolambda.uibook.annotations.UIBook
import nolambda.uibook.browser.BookHost
import nolambda.uibook.databinding.ItemTextImageBinding
import nolambda.uibook.databinding.ItemWithBooleanBinding

@UIBook(name = "TextView")
fun BookHost.createTextView(text: String): TextView {
    /**
     * This will draw text
     */
    return TextView(context).apply {
        this.text = text
        setTextColor(Color.RED)
    }
}

@UIBook(name = "Input")
fun BookHost.createInput(input: String, hint: String): View {
    return EditText(context).apply {
        setText(input)
        setHint(hint)
    }
}

@UIBook(name = "TextWithImage")
fun BookHost.createTextWithImage(title: String): View {
    val binding = ItemTextImageBinding.inflate(inflater, parent, false)
    binding.txtTitle.text = title
    return binding.root
}

@UIBook(name = "BooleanTest")
fun BookHost.createBooleanText(isBold: Boolean): View {
    val binding = ItemWithBooleanBinding.inflate(inflater, parent, false)
    binding.txtSubtitle.setTypeface(
        null, if (isBold) {
            Typeface.BOLD
        } else {
            Typeface.NORMAL
        }
    )
    return binding.root
}