package nolambda.uibook.components.bookform

import nolambda.uibook.browser.form.InputCreator

/**
 * Represent input data for [BookForm]
 *
 * Contains:
 * - the actual view state that will be passed into book function parameter
 * - input creators for interacting with input and will call [setViewState] to update the view state
 * - [setViewState] to update the view state
 */
class InputData(
    val viewState: Array<Any>,
    val inputCreators: Array<InputCreator>,
    val setViewState: (Int, Any) -> Unit
)
