package nolambda.uibook.browser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import nolambda.uibook.browser.config.AppBrowserConfig
import nolambda.uibook.components.bookform.GlobalState
import nolambda.uibook.components.booklist.BookList
import nolambda.uibook.factory.LibraryLoader

class UIBookListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val library = AppBrowserConfig.libraryLoader.load()
        val names = library.getBookFactories().map { it.getMetaData().name }

        setContent {
            val context = LocalContext.current
            BookList(bookNames = names) { index ->

                // Disable the full screen mode first
                GlobalState.fullScreenMode.setValue(false)

                UIBookActivity.start(context, index)
            }
        }
    }
}
