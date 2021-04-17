package nolambda.uibook.browser

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import nolambda.uibook.factory.LibraryLoader

class UIBookActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val index = intent.extras?.getInt(EXTRA_INDEX) ?: 0

        val library = LibraryLoader.load()
        val factory = library.getBookFactories()[index]

        setContentView(factory.getView(this))

        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
        }
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