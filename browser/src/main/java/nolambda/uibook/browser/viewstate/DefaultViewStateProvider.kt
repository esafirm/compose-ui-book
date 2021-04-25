package nolambda.uibook.browser.viewstate

import nolambda.uibook.annotations.BookMetaData
import nolambda.uibook.browser.form.ViewState

class DefaultViewStateProvider : ViewStateProvider {

    override fun createViewState(book: BookMetaData): ViewState {
        val parameters = book.parameters

        return ViewState(parameters.size) {
            getDefaultType(parameters[it].type)
        }
    }

    private fun getDefaultType(type: String): Any {
        return when (type) {
            "java.lang.String" -> ""
            "int" -> 0
            "boolean" -> false
            else -> error("No default value provided for $type")
        }
    }
}