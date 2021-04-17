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
import nolambda.uibook.browser.viewstate.DefaultViewStateProvider
import nolambda.uibook.browser.viewstate.ViewStateProvider

typealias OnUpdate = (Array<Any>) -> View
typealias ViewState = Array<Any>

class FormCreator(
    private val context: Context,
    private val meta: BookMetaData,
    private val viewStateProvider: ViewStateProvider = DefaultViewStateProvider()
) {

    private val inflater by lazy { LayoutInflater.from(context) }
    private val binding by lazy { ViewFormBinding.inflate(inflater) }

    private fun createInputs(onUpdate: OnUpdate): ViewState {
        val viewState: Array<Any> = viewStateProvider.createViewState(meta)

        val setViewState = { index: Int, value: Any ->
            viewState[index] = value

            // Invalidate view
            val child = onUpdate(viewState)
            binding.containerComponent.removeAllViews()
            binding.containerComponent.addView(child)
        }

        meta.parameters.forEachIndexed { index, parameter ->

            val input = ViewInputBinding.inflate(inflater, binding.containerInput, false).apply {
                inpLayout.hint = parameter.name
                inpEditText.addTextChangedListener {
                    setViewState(index, it.toString())
                }
            }

            binding.containerInput.addView(input.root)
        }

        return viewState
    }

    fun create(onUpdate: OnUpdate): View {
        val viewState = createInputs(onUpdate)

        // First render
        binding.containerComponent.addView(onUpdate(viewState))

        val functionCode = meta.function.replace("return ", "")
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