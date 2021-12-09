package nolambda.uibook.components.bookform

import nolambda.uibook.browser.form.ComposeInputCreator

class InputData(
    val viewState: Array<Any>,
    val inputCreator: ComposeInputCreator,
    val setViewState: (Int, Any) -> Unit
)