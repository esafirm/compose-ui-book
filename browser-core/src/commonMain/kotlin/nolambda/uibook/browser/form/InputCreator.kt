package nolambda.uibook.browser.form

import androidx.compose.runtime.Composable
import nolambda.uibook.annotations.FunctionParameter

/**
 * Input creator is a factory interface to create the input in UI Book
 * This input add interactivity to the book so user can understand the book better
 *
 * @see [DefaultInputCreator]
 */
interface InputCreator {

    @Composable
    fun CreateInput(
        parameter: FunctionParameter,
        defaultState: Any,
        setViewState: (Any) -> Unit,
    )
}
