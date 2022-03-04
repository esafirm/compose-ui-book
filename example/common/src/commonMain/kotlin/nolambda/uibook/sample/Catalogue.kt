package nolambda.uibook.sample

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import nolambda.uibook.annotations.UIBook
import nolambda.uibook.browser.BookHost

@UIBook(name = "Compose code")
@Composable
fun BookHost.SampleText(text: String) {
    Text(text = text)
}