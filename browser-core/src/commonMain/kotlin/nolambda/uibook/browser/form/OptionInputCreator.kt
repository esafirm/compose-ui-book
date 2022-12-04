package nolambda.uibook.browser.form

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import nolambda.uibook.annotations.FunctionParameter

/**
 * [InputCreator] that create an option buttons that values and type determined in the constructor
 *
 * @param items list of options that will be displayed in the UI
 */
abstract class OptionInputCreator<T : Any>(
    private val items: List<T>
) : InputCreator {

    @Composable
    override fun CreateInput(
        parameter: FunctionParameter,
        defaultState: Any,
        setViewState: (Any) -> Unit
    ) {
        val (selectedItem, setSelectedItem) = remember { mutableStateOf(defaultState) }

        Column {
            Text(text = parameter.name, modifier = Modifier.fillMaxWidth())

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items.forEach { item ->
                    Button(
                        onClick = {
                            setViewState(item)
                            setSelectedItem(item)
                        },
                        modifier = Modifier.weight(1F),
                        colors = if (selectedItem == item) {
                            ButtonDefaults.buttonColors(
                                backgroundColor = MaterialTheme.colors.secondary,
                                contentColor = Color.White
                            )
                        } else {
                            ButtonDefaults.buttonColors()
                        }
                    ) {
                        Text(text = item.toString())
                    }
                }
            }
        }
    }
}
