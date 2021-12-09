package nolambda.uibook.browser.form

import android.view.LayoutInflater
import android.view.View
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ComposeView
import nolambda.uibook.annotations.BookMetaData
import nolambda.uibook.browser.BookHost
import nolambda.uibook.browser.databinding.ViewFormBinding
import nolambda.uibook.browser.viewstate.DefaultViewStateProvider
import nolambda.uibook.browser.viewstate.ViewStateProvider
import nolambda.uibook.components.bookform.BookForm
import nolambda.uibook.components.bookform.InputData
import nolambda.uibook.factory.BookConfig


typealias OnUpdate = BookHost.(Array<Any>) -> View
typealias ViewState = Array<Any>

class FormCreator(
    config: BookConfig,
    private val meta: BookMetaData,
    private val viewStateProvider: ViewStateProvider = DefaultViewStateProvider(),
    private val inputCreator: InputCreator = DefaultInputCreator()
) {

    private val context = config.context()

    private val inflater by lazy { LayoutInflater.from(context) }
    private val binding by lazy { ViewFormBinding.inflate(inflater) }

    private val bookHost by lazy { BookHost(context, binding.containerComponent) }

    fun create(onUpdate: OnUpdate): View {
        val viewState: Array<Any> = viewStateProvider.createViewState(meta)

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
                    meta = meta,
                    bookView = view.value,
                    inputData = InputData(
                        viewState = viewState,
                        inputCreator = inputCreator,
                        setViewState = setViewState
                    )
                )
            }
        }
    }
}