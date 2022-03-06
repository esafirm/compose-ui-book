package nolambda.uibook.sample.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import nolambda.uibook.annotations.FunctionParameter
import nolambda.uibook.browser.form.InputCreator

class ButtonInput : InputCreator {

    @Composable
    override fun CreateInput(
        parameter: FunctionParameter,
        defaultState: Any,
        setViewState: (Any) -> Unit
    ) {
        Button(
            onClick = { setViewState("RANDOM STRING") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Random UUID")
        }
    }
}