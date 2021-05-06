package nolambda.uibook.browser.form

import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import androidx.core.widget.addTextChangedListener
import nolambda.uibook.annotations.FunctionParameter
import nolambda.uibook.browser.ParameterTypes
import nolambda.uibook.browser.databinding.ViewFormBinding
import nolambda.uibook.browser.databinding.ViewInputBinding
import nolambda.uibook.browser.databinding.ViewSwitchBinding

class DefaultInputCreator : InputCreator {
    override fun createInput(
        inflater: LayoutInflater,
        parent: ViewFormBinding,
        parameter: FunctionParameter,
        defaultState: Any,
        setViewState: (Any) -> Unit
    ): View {
        val type = parameter.type
        val name = parameter.name
        if (type == ParameterTypes.STRING) {
            return createFreeTextInput(inflater, parent, name, defaultState = defaultState, setViewState = setViewState)
        }
        if (ParameterTypes.isNumber(type)) {
            return createFreeTextInput(inflater, parent, name, InputType.TYPE_CLASS_NUMBER, defaultState) {
                if (it.isBlank()) {
                    setViewState(defaultState)
                    return@createFreeTextInput
                }
                if (type == ParameterTypes.INT) {
                    setViewState(it.toInt())
                    return@createFreeTextInput
                }
                if (type == ParameterTypes.FLOAT) {
                    setViewState(it.toFloat())
                    return@createFreeTextInput
                }
                error("Type $type is not handled on type conversion")
            }
        }
        if (type == ParameterTypes.BOOLEAN) {
            return createBooleanInput(inflater, parent, name, setViewState)
        }
        error("There's no input handler for $name with type $type")
    }

    private fun createFreeTextInput(
        inflater: LayoutInflater,
        parent: ViewFormBinding,
        hint: String,
        inputType: Int = InputType.TYPE_CLASS_TEXT,
        defaultState: Any,
        setViewState: (String) -> Unit
    ): View {
        val input = ViewInputBinding.inflate(inflater, parent.containerInput, false).apply {
            inpLayout.hint = hint
            inpEditText.inputType = inputType
            inpEditText.setText(defaultState.toString())
            inpEditText.addTextChangedListener {
                setViewState(it.toString())
            }
        }
        return input.root
    }

    private fun createBooleanInput(
        inflater: LayoutInflater,
        parent: ViewFormBinding,
        hint: String,
        setViewState: (Any) -> Unit
    ): View {
        val input = ViewSwitchBinding.inflate(inflater, parent.containerComponent, false).apply {
            inputSwitch.text = hint
            inputSwitch.setOnCheckedChangeListener { _, isChecked ->
                setViewState(isChecked)
            }
        }
        return input.root
    }
}