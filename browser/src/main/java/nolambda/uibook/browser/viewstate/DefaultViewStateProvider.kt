package nolambda.uibook.browser.viewstate

import nolambda.uibook.annotations.BookMetaData
import nolambda.uibook.browser.ParameterTypes
import nolambda.uibook.browser.form.ViewState

class DefaultViewStateProvider : ViewStateProvider {

    override fun createViewState(book: BookMetaData): ViewState {
        val parameters = book.parameters
        return ViewState(parameters.size) {
            ParameterTypes.getDefaultStateForType(parameters[it].type)
        }
    }
}