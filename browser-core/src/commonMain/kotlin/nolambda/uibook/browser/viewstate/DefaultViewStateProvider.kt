package nolambda.uibook.browser.viewstate

import nolambda.uibook.annotations.BookMetaData
import nolambda.uibook.browser.ParameterTypes
import nolambda.uibook.browser.form.ViewState

class DefaultViewStateProvider : ViewStateProvider {

    override fun createViewState(book: BookMetaData): ViewState {
        val parameters = book.parameters
        return ViewState(parameters.size) { index ->
            val param = parameters[index]
            val defaultValue = param.defaultValue

            when (param.type) {
                ParameterTypes.STRING -> defaultValue
                ParameterTypes.INT -> defaultValue.toInt()
                ParameterTypes.FLOAT -> defaultValue.toFloat()
                ParameterTypes.BOOLEAN -> defaultValue.toBoolean()
                else -> error("Type ${param.type} has no default state")
            }
        }
    }
}