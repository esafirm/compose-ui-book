package nolambda.uibook.browser

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.ComposeView
import nolambda.uibook.factory.ActivityHost
import nolambda.uibook.factory.AndroidBookConfig
import nolambda.uibook.factory.LibraryLoader

class UIBookActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val index = intent.extras?.getInt(EXTRA_INDEX) ?: 0

        val library = LibraryLoader.load()
        val factory = library.getBookFactories()[index]

        val host = ActivityHost(this)
        val config = AndroidBookConfig(host) {
            finish()
        }
        setContentView(ComposeView(this).apply {
            setContent {
                factory.getBook(config).invoke()
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {

        private const val EXTRA_INDEX = "Extra.BookDetail.Index"

        fun start(context: Context, bookIndex: Int) {
            context.startActivity(Intent(context, UIBookActivity::class.java).apply {
                putExtra(EXTRA_INDEX, bookIndex)
            })
        }
    }
}