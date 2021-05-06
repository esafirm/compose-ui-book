package nolambda.uibook.browser.form

import android.view.LayoutInflater
import android.view.View
import nolambda.uibook.annotations.FunctionParameter
import nolambda.uibook.browser.databinding.ViewFormBinding

interface InputCreator {
    fun createInput(
        inflater: LayoutInflater,
        parent: ViewFormBinding,
        parameter: FunctionParameter,
        setViewState: (Any) -> Unit,
    ): View
}