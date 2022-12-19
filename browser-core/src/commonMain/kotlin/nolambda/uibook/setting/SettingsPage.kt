package nolambda.uibook.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun SettingPage() {
    Column {
        Text("Canvas")

        SettingRow("GridSize", "8", "Dp") {
        }
        SettingRow("Grid Color", "#000") {
        }
        SettingRow("Canvas Color", "#FFF") {
        }
    }
}

@Composable
private fun SettingRow(
    name: String,
    value: String,
    suffix: String = "",
    onValueChange: (String) -> Unit,
) {
    Row {
        Text(name)
        BasicTextField(value, onValueChange)

        if (suffix.isNotEmpty()) {
            Text(suffix)
        }
    }
}


