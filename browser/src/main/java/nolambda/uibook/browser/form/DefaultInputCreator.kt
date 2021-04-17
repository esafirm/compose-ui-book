package nolambda.uibook.browser.form

import android.view.LayoutInflater
import android.view.View
import androidx.core.widget.addTextChangedListener
import nolambda.uibook.annotations.FunctionParameter
import nolambda.uibook.browser.databinding.ViewFormBinding
import nolambda.uibook.browser.databinding.ViewInputBinding

class DefaultInputCreator : InputCreator {
    override fun createInput(
        inflater: LayoutInflater,
        parent: ViewFormBinding,
        parameter: FunctionParameter,
        setViewState: (Any) -> Unit
    ): View {
        val input = ViewInputBinding.inflate(inflater, parent.containerInput, false).apply {
            inpLayout.hint = parameter.name
            inpEditText.addTextChangedListener {
                setViewState(it.toString())
            }
        }
        return input.root
    }
}