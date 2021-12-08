package nolambda.uibook.browser.form

import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ComposeView
import androidx.core.content.ContextCompat
import nolambda.uibook.annotations.BookMetaData
import nolambda.uibook.browser.BookForm
import nolambda.uibook.browser.BookHost
import nolambda.uibook.browser.InputData
import nolambda.uibook.browser.R
import nolambda.uibook.browser.databinding.ViewFormBinding
import nolambda.uibook.browser.databinding.ViewSeparatorBinding
import nolambda.uibook.browser.measurement.MeasurementHelperImpl
import nolambda.uibook.browser.measurement.MeasurementOverlayView
import nolambda.uibook.browser.viewstate.DefaultViewStateProvider
import nolambda.uibook.browser.viewstate.ViewStateProvider
import nolambda.uibook.factory.BookConfig


typealias OnUpdate = BookHost.(Array<Any>) -> View
typealias ViewState = Array<Any>

class FormCreator(
    private val config: BookConfig,
    private val meta: BookMetaData,
    private val viewStateProvider: ViewStateProvider = DefaultViewStateProvider(),
    private val inputCreator: InputCreator = DefaultInputCreator()
) {

    private val context = config.context()

    private val inflater by lazy { LayoutInflater.from(context) }
    private val binding by lazy { ViewFormBinding.inflate(inflater) }
    private val componentCreator by lazy { ComponentCreator(context) }

    private val bookHost by lazy { BookHost(context, binding.containerComponent) }

    private fun createInputViews(viewState: Array<Any>, onUpdate: OnUpdate): List<View> {
        val setViewState = { index: Int, value: Any ->
            viewState[index] = value

            // Invalidate view
            val child = onUpdate(bookHost, viewState)
            if (meta.isComposeFunction.not()) {
                binding.containerComponent.removeAllViews()
                binding.containerComponent.addView(child)
            }
        }

        return meta.parameters.mapIndexed { index, parameter ->

            val isAddSeparator = index != 0
            if (isAddSeparator) {
                ViewSeparatorBinding.inflate(inflater, binding.containerInput, true)
            }

            val setViewStateForIndex = { value: Any ->
                setViewState(index, value)
            }

            val input = inputCreator.createInput(
                inflater = inflater,
                parent = binding,
                parameter = parameter,
                defaultState = viewState[index],
                setViewState = setViewStateForIndex
            )

            input
        }
    }

    private fun setupMeasurementView(): MeasurementOverlayView {
        val measurementHelper = MeasurementHelperImpl(binding.containerComponent)
        val measurementView = componentCreator.createMeasurementView(measurementHelper)
        binding.containerTop.addView(measurementView)
        return measurementView
    }

    private fun setupToolbar(measurementView: MeasurementOverlayView) {
        binding.toolbar.apply {
            title = meta.name
            setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)
            setNavigationOnClickListener { config.onExit() }
            inflateMenu(R.menu.menu_book)

            setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.menu_measurement -> {
                        val isEnable = toggleMeasurement(measurementView)
                        val icon = if (isEnable) {
                            R.drawable.ic_measurement_enabled
                        } else {
                            R.drawable.ic_measurement_disabled
                        }
                        item.icon = ContextCompat.getDrawable(context, icon)

                        val message = if (isEnable) "enabled" else "disabled"
                        Toast.makeText(binding.root.context, "Measurement $message", Toast.LENGTH_SHORT).show()
                    }
                }
                false
            }
        }
    }

    private fun toggleMeasurement(measurementView: MeasurementOverlayView): Boolean {
        val isEnabled = measurementView.visibility == View.VISIBLE
        val nextState = if (isEnabled) View.GONE else View.VISIBLE
        measurementView.visibility = nextState
        return isEnabled.not()
    }

    fun create(onUpdate: OnUpdate): View {
        val viewState: Array<Any> = viewStateProvider.createViewState(meta)
        val composeInputCreator = DefaultComposeInputCreator()

        return ComposeView(context).apply {
            setContent {

                val view = remember { mutableStateOf(onUpdate(bookHost, viewState)) }
                val setViewState = remember {
                    { index: Int, value: Any ->
                        viewState[index] = value

                        // Invalidate view
                        val child = onUpdate(bookHost, viewState)
                        if (meta.isComposeFunction.not()) {
                            view.value = child
                        }
                    }
                }

                BookForm(
                    metaData = meta,
                    bookView = view.value,
                    inputData = InputData(
                        viewState = viewState,
                        inputCreator = composeInputCreator,
                        setViewState = setViewState
                    )
                )
            }
        }
    }
}