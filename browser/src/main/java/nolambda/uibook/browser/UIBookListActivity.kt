package nolambda.uibook.browser

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import nolambda.kommonadapter.attach
import nolambda.kommonadapter.simple.SimpleAdapter
import nolambda.kommonadapter.viewbinding.map
import nolambda.uibook.browser.databinding.ActivityUiBookListBinding
import nolambda.uibook.browser.databinding.ItemUiBookBinding
import nolambda.uibook.factory.LibraryLoader

class UIBookListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val simpleAdapter = SimpleAdapter(this).create {
            map(ItemUiBookBinding::inflate, String::class) { _, name ->
                txtName.text = name
            }
        }

        val library = LibraryLoader.load()
        simpleAdapter.pushData(library.getBookFactories().map { it.getMetaData().name })

        val binding = ActivityUiBookListBinding.inflate(layoutInflater)
        binding.recycler.attach(adapter = simpleAdapter) { index, _ ->
            UIBookActivity.start(this, index)
        }

        setContentView(binding.root)
    }
}