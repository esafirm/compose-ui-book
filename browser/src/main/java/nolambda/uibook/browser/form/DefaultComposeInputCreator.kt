package nolambda.uibook.browser.form

import android.text.InputType
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import nolambda.uibook.annotations.FunctionParameter
import nolambda.uibook.browser.ParameterTypes

class DefaultComposeInputCreator : ComposeInputCreator {

    @Composable
    private fun FreeTextInput(
        hint: String,
        inputType: Int = InputType.TYPE_CLASS_TEXT,
        defaultState: Any,
        setViewState: (String) -> Unit
    ) {
        val textState = remember { mutableStateOf(TextFieldValue(defaultState.toString())) }

        TextField(
            value = textState.value,
            label = { Text(hint) },
            onValueChange = { value -> textState.value = value },
            modifier = Modifier.fillMaxWidth()
        )

        // Update the view state
        val text = textState.value.text
        LaunchedEffect(text) { setViewState(text) }
    }

    @Composable
    private fun BooleanInput(
        hint: String,
        setViewState: (Any) -> Unit
    ) {
        val checkedState = remember { mutableStateOf(true) }
        Row(modifier = Modifier.fillMaxWidth()) {

            Text(text = hint, modifier = Modifier.weight(1F))

            Switch(
                checked = checkedState.value,
                onCheckedChange = { checkedState.value = it },
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }

        // Update view state
        val isChecked = checkedState.value
        LaunchedEffect(isChecked) { setViewState(isChecked) }
    }

    @Composable
    override fun CreateInput(parameter: FunctionParameter, defaultState: Any, setViewState: (Any) -> Unit) {
        val type = parameter.type
        val name = parameter.name
        if (type == ParameterTypes.STRING) {
            FreeTextInput(name, defaultState = defaultState, setViewState = setViewState)
            return
        }
        if (ParameterTypes.isNumber(type)) {
            FreeTextInput(name, InputType.TYPE_CLASS_NUMBER, defaultState) {
                if (it.isBlank()) {
                    setViewState(defaultState)
                    return@FreeTextInput
                }
                if (type == ParameterTypes.INT) {
                    setViewState(it.toInt())
                    return@FreeTextInput
                }
                if (type == ParameterTypes.FLOAT) {
                    setViewState(it.toFloat())
                    return@FreeTextInput
                }
                error("Type $type is not handled on type conversion")
            }
            return
        }
        if (type == ParameterTypes.BOOLEAN) {
            BooleanInput(name, setViewState)
            return
        }
        error("There's no input handler for $name with type $type")
    }
}