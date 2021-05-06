package nolambda.uibook.samples

import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import nolambda.uibook.annotations.FunctionParameter
import nolambda.uibook.browser.databinding.ViewFormBinding
import nolambda.uibook.browser.form.InputCreator
import java.util.*

class ButtonInput : InputCreator {

    override fun createInput(
        inflater: LayoutInflater,
        parent: ViewFormBinding,
        parameter: FunctionParameter,
        defaultState: Any,
        setViewState: (Any) -> Unit
    ): View {
        val context = parent.root.context
        return Button(context).apply {
            text = "Random UUID"
            setOnClickListener {
                setViewState(UUID.randomUUID().toString())
            }
        }
    }
}