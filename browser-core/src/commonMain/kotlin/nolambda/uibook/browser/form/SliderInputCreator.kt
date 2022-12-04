package nolambda.uibook.browser.form

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import nolambda.uibook.annotations.FunctionParameter
import nolambda.uibook.utils.asNumberType

/**
 * [InputCreator] that create a slider that the progression determined in the constructor
 *
 * @param items list of options that will be displayed in the UI
 */
open class SliderInputCreator(
    private val steps: Int = 0,
    private val valueRange: ClosedFloatingPointRange<Float> = 0f..1f
) : InputCreator {

    @Composable
    override fun CreateInput(
        parameter: FunctionParameter,
        defaultState: Any,
        setViewState: (Any) -> Unit
    ) {
        if (parameter.isNumberType.not()) {
            error("SliderInputCreator only support number type")
        }

        val (selectedItem, setSelectedItem) = remember { mutableStateOf(defaultState) }

        Column {
            Text(text = parameter.name, modifier = Modifier.fillMaxWidth())

            Slider(
                value = selectedItem.toString().toFloat(),
                onValueChange = {
                    setViewState(it.asNumberType(parameter.type))
                    setSelectedItem(it)
                },
                valueRange = valueRange,
                steps = steps,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
