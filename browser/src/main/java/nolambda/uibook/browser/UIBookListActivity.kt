package nolambda.uibook.browser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import nolambda.uibook.factory.LibraryLoader

class UIBookListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val library = LibraryLoader.load()
        val names = library.getBookFactories().map { it.getMetaData().name }

        setContent {
            BookList(bookNames = names)
        }
    }
}
