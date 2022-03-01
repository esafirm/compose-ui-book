package nolambda.uibook.samples

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import nolambda.uibook.annotations.FunctionParameter
import nolambda.uibook.browser.form.InputCreator
import java.util.UUID

class ButtonInput : InputCreator {

    @Composable
    override fun CreateInput(
        parameter: FunctionParameter,
        defaultState: Any,
        setViewState: (Any) -> Unit
    ) {
        Button(
            onClick = { setViewState(UUID.randomUUID().toString()) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Random UUID")
        }
    }
}