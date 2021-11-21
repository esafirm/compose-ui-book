package nolambda.uibook.browser.form

import android.view.LayoutInflater
import android.view.View
import nolambda.uibook.annotations.FunctionParameter
import nolambda.uibook.browser.databinding.ViewFormBinding

/**
 * Input creator is a factory interface to create the input in UI Book
 * This input add interactivity to the book so user can understand the book better
 *
 * @see [DefaultInputCreator]
 */
interface InputCreator {
    fun createInput(
        inflater: LayoutInflater,
        parent: ViewFormBinding,
        parameter: FunctionParameter,
        defaultState: Any,
        setViewState: (Any) -> Unit,
    ): View
}