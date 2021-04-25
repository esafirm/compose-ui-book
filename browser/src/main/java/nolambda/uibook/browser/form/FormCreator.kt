package nolambda.uibook.browser.form

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.google.android.material.tabs.TabLayout
import io.github.kbiakov.codeview.adapters.Options
import io.github.kbiakov.codeview.highlight.ColorTheme
import nolambda.uibook.annotations.BookMetaData
import nolambda.uibook.browser.BookHost
import nolambda.uibook.browser.databinding.ViewFormBinding
import nolambda.uibook.browser.measurement.MeasurementHelperImpl
import nolambda.uibook.browser.show
import nolambda.uibook.browser.viewstate.DefaultViewStateProvider
import nolambda.uibook.browser.viewstate.ViewStateProvider

typealias OnUpdate = BookHost.(Array<Any>) -> View
typealias ViewState = Array<Any>

class FormCreator(
    private val context: Context,
    private val meta: BookMetaData,
    private val viewStateProvider: ViewStateProvider = DefaultViewStateProvider(),
    private val inputCreator: InputCreator = DefaultInputCreator()
) {

    private val inflater by lazy { LayoutInflater.from(context) }
    private val binding by lazy { ViewFormBinding.inflate(inflater) }
    private val componentCreator by lazy { ComponentCreator(context) }

    private val bookHost by lazy { BookHost(context, binding.containerComponent) }

    private fun createInputs(onUpdate: OnUpdate): ViewState {
        val viewState: Array<Any> = viewStateProvider.createViewState(meta)

        val setViewState = { index: Int, value: Any ->
            viewState[index] = value

            // Invalidate view
            val child = onUpdate(bookHost, viewState)
            binding.containerComponent.removeAllViews()
            binding.containerComponent.addView(child)
        }

        meta.parameters.forEachIndexed { index, parameter ->

            val setViewStateForIndex = { value: Any ->
                setViewState(index, value)
            }

            val input = inputCreator.createInput(
                inflater = inflater,
                parent = binding,
                parameter = parameter,
                setViewState = setViewStateForIndex
            )

            binding.containerInput.addView(input)
        }

        return viewState
    }

    private fun setupMeasurementView() {
        var decorView: View? = null
        if (context is Activity) {
            decorView = context.window.decorView
        }
        val measurementHelper = MeasurementHelperImpl(binding.containerComponent, decorView)
        val measurementView = componentCreator.createMeasurementView(measurementHelper)
        binding.containerTop.addView(measurementView)
    }

    fun create(onUpdate: OnUpdate): View {
        setupMeasurementView()

        val viewState = createInputs(onUpdate)

        // First render
        binding.containerComponent.addView(onUpdate(bookHost, viewState))

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