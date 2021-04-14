package nolambda.uibook.browser

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.core.widget.addTextChangedListener
import com.google.android.material.tabs.TabLayout
import io.github.kbiakov.codeview.adapters.Options
import io.github.kbiakov.codeview.highlight.ColorTheme
import nolambda.uibook.annotations.BookMetaData
import nolambda.uibook.browser.databinding.ViewFormBinding
import nolambda.uibook.browser.databinding.ViewInputBinding

class FormCreator(
    private val context: Context,
    private val bookMetaData: BookMetaData
) {

    private fun createInflater(): LayoutInflater =
        LayoutInflater.from(context)

    fun create(onUpdate: (String) -> View): View {
        val inflater = createInflater()
        val binding = ViewFormBinding.inflate(inflater)

        val input = ViewInputBinding.inflate(inflater, binding.containerInput, false).apply {
            inpLayout.hint = "Input 1"
            inpEditText.addTextChangedListener {
                val child = onUpdate(it.toString())
                binding.containerComponent.removeAllViews()
                binding.containerComponent.addView(child)
            }
        }

        binding.containerInput.addView(input.root)
        binding.containerComponent.addView(onUpdate(""))

        val functionCode = bookMetaData.function.replace("return ", "")
            .removeSurrounding("{", "}")
            .trimIndent()

        binding.txtCode.setOptions(
            Options.get(context)
                .withCode(functionCode)
                .withLanguage("kotlin")
                .withTheme(ColorTheme.MONOKAI)
        )

        binding.tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val isSourceCode = tab.position == 0
                binding.txtCode.show(isSourceCode, animate = true)
                binding.containerInput.show(!isSourceCode, animate = true)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        return binding.root
    }
}