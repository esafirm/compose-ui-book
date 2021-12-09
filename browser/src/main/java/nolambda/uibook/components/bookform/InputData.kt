package nolambda.uibook.components.bookform

import nolambda.uibook.browser.form.InputCreator

class InputData(
    val viewState: Array<Any>,
    val inputCreator: InputCreator,
    val setViewState: (Int, Any) -> Unit
)