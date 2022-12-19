package nolambda.uibook.setting

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import nolambda.uibook.components.common.ClickableText

@Suppress("PrivatePropertyName")
private val SETTING_MENU = listOf(
    SettingMenu("Canvas") { CanvasSetting() }
)

private class SettingMenu(
    val name: String,
    val components: @Composable () -> Unit,
)

/**
 * A setting page that contains a list of settings that will be consumer by
 * the browser app
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SettingPage(
    modifier: Modifier = Modifier,
) {
    val (selectedMenu, setSelectedMenu) = remember { mutableStateOf(SETTING_MENU.first()) }

    Box(modifier = Modifier
        .background(MaterialTheme.colors.background)
        .padding(16.dp)
        .clickable(enabled = false, onClick = {})
        .composed { modifier }
    ) {
        Column {
            Text("Settings", style = MaterialTheme.typography.h4)
            Divider(modifier = Modifier.padding(vertical = 8.dp))

            Row {
                LazyColumn(
                    modifier = Modifier
                        .weight(1F)
                        .wrapContentSize()
                ) {
                    items(SETTING_MENU) { menu ->

                        val itemModifier = if (menu == selectedMenu) {
                            Modifier.background(Color.LightGray.copy(alpha = 0.5f))
                        } else Modifier

                        ClickableText(
                            text = menu.name,
                            style = MaterialTheme.typography.subtitle1.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = itemModifier.padding(16.dp),
                            onClick = { setSelectedMenu(menu) }
                        )
                    }
                }
                Divider(modifier = Modifier.width(1.dp).fillMaxHeight())

                // Draw setting pane
                Box(modifier = Modifier.weight(3F)) {
                    selectedMenu.components()
                }
            }
        }
    }
}

@Composable
private fun CanvasSetting() {
    Column(modifier = Modifier.padding(16.dp)) {
        val itemModifier = Modifier.padding(bottom = 16.dp)

        SettingRow("GridSize", "8", "Dp", modifier = itemModifier) {
        }

        SettingRow("Grid Color", "#000", modifier = itemModifier) {
        }

        SettingRow("Canvas Color", "#FFF", modifier = itemModifier) {
        }
    }
}

@Composable
private fun SettingRow(
    name: String,
    value: String,
    suffix: String = "",
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
) {
    Row(modifier, verticalAlignment = Alignment.CenterVertically) {
        Text(
            name,
            fontSize = MaterialTheme.typography.subtitle1.fontSize
        )

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.padding(horizontal = 8.dp),
            decorationBox = { innerTextField ->
                Row(
                    Modifier
                        .background(Color.LightGray, RoundedCornerShape(percent = 30))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    innerTextField()
                }
            }
        )

        if (suffix.isNotEmpty()) {
            Text(suffix, modifier = Modifier)
        }
    }
}


