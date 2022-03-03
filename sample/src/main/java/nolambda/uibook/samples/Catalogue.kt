package nolambda.uibook.samples

import android.graphics.Color
import android.graphics.Typeface
import android.util.TypedValue
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import nolambda.uibook.annotations.State
import nolambda.uibook.annotations.UIBook
import nolambda.uibook.annotations.code.CodeSpec
import nolambda.uibook.browser.AndroidBookHost
import nolambda.uibook.browser.BookHost
import nolambda.uibook.databinding.ItemTextImageBinding
import nolambda.uibook.databinding.ItemWithBooleanBinding

@UIBook(name = "TextView")
fun AndroidBookHost.createTextView(text: String): TextView {
    /**
     * This will draw text
     */
    return TextView(context).apply {
        this.text = text
        setTextColor(Color.RED)
    }
}

//@UIBook(
//    name = "Custom Input",
//    inputCreator = ButtonInput::class
//)
//fun BookHost.createTextViewCustom(text: String): TextView {
//    return TextView(context).apply {
//        this.text = text
//    }
//}
//
//@UIBook(name = "Input")
//fun BookHost.createInput(input: String, hint: String): View {
//    return EditText(context).apply {
//        setText(input)
//        setHint(hint)
//    }
//}
//
//@UIBook(name = "TextWithImage")
//fun BookHost.createTextWithImage(title: String): View {
//    val binding = ItemTextImageBinding.inflate(inflater, null, false)
//    binding.txtTitle.text = title
//    return binding.root
//}
//
//@UIBook(name = "This is a name")
//fun BookHost.createBooleanText(isBold: Boolean): View {
//    val binding = ItemWithBooleanBinding.inflate(inflater, null, false)
//    binding.txtSubtitle.setTypeface(
//        null, if (isBold) {
//            Typeface.BOLD
//        } else {
//            Typeface.NORMAL
//        }
//    )
//    return binding.root
//}
//
//@UIBook(name = "Custom code")
//@CodeSpec(
//    code = """
//        <TextView android:text="testing"
//                  android:textSize="17sp" />
//    """,
//    trimIndent = true,
//    language = "xml"
//)
//fun BookHost.createCustomCodeComponent(
//    text: String,
//    @State(defaultValue = "17") textSize: Float
//): View {
//    return TextView(context).apply {
//        this.text = if (text.isBlank()) "testing" else text
//        this.textSize =
//            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, textSize, context.resources.displayMetrics)
//    }
//}

@UIBook(name = "Compose code")
@Composable
fun BookHost.SampleText(text: String) {
    Text(text = text)
}

@ExperimentalCoilApi
@UIBook(name = "Circular Image Sample")
@Composable
fun BookHost.CircularImage(
    @State(defaultValue = "https://dimsumaskitea.com/logo.png") imageUrl: String,
    @State(defaultValue = "Dimsum Askitea") title: String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = rememberImagePainter(imageUrl),
            contentDescription = "avatar",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .border(2.dp, androidx.compose.ui.graphics.Color.Black, CircleShape)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            fontSize = 22.sp
        )
    }
}