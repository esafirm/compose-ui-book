package nolambda.uibook.browser.form

import androidx.compose.runtime.Composable
import nolambda.uibook.annotations.BookMetaData
import nolambda.uibook.browser.BookHost
import nolambda.uibook.browser.config.AppBrowserConfig
import nolambda.uibook.browser.viewstate.DefaultViewStateProvider
import nolambda.uibook.browser.viewstate.ViewStateProvider
import nolambda.uibook.components.bookform.BookForm
import nolambda.uibook.components.bookform.InputData
import nolambda.uibook.factory.BookConfig

typealias ComposeEmitter = @Composable () -> Unit
typealias ComposeViewCreator = @Composable BookHost.(Array<Any>) -> Unit
typealias ViewState = Array<Any>

class FormCreator(
    config: BookConfig,
    private val meta: BookMetaData,
    private val onUpdateState: (Array<Any>) -> Unit,
    private val onChildCreation: ComposeViewCreator,
    private val viewStateProvider: ViewStateProvider = DefaultViewStateProvider(),
    private val inputCreator: InputCreator = DefaultInputCreator()
) {

    private val bookHost by lazy { AppBrowserConfig.bookHost }

    fun create(): ComposeEmitter {
        val viewState: Array<Any> = viewStateProvider.createViewState(meta)
        val setViewState = { index: Int, value: Any ->
            viewState[index] = value
            onUpdateState(viewState)
        }

        return {
            BookForm(
                meta = meta,
                bookView = { onChildCreation(bookHost, viewState) },
                inputData = InputData(
                    viewState = viewState,
                    inputCreator = inputCreator,
                    setViewState = setViewState
                )
            )
        }
    }
}