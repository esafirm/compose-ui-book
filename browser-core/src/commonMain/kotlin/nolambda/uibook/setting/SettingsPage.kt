package nolambda.uibook.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import nolambda.uibook.browser.config.AppBrowserConfig
import nolambda.uibook.browser.config.CanvasSetting
import nolambda.uibook.browser.config.SettingStore
import nolambda.uibook.components.common.ClickableText
import nolambda.uibook.utils.toColor

@Suppress("PrivatePropertyName")
private val SETTING_MENU = listOf(
    SettingMenu("Canvas") { scope -> CanvasSetting(scope) }
)

private class SettingMenu(
    val name: String,
    val components: @Composable (CoroutineScope) -> Unit,
)

/**
 * A setting page that contains a list of settings that will be consumer by
 * the browser app
 */
@Composable
fun SettingPage(
    modifier: Modifier = Modifier,
    onRequestToClose: (() -> Unit)? = null,
) {
    val (selectedMenu, setSelectedMenu) = remember { mutableStateOf(SETTING_MENU.first()) }
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier
        .background(MaterialTheme.colors.background)
        .padding(16.dp)
        .clickable(enabled = false, onClick = {})
        .composed { modifier }
    ) {
        Column {
            Row {
                Text(
                    text = "Settings",
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.weight(1F)
                )

                if (onRequestToClose != null) {
                    IconButton(onClick = onRequestToClose) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close"
                        )
                    }
                }
            }
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
                    selectedMenu.components(scope)
                }
            }
        }
    }
}

@Composable
private fun CanvasSetting(
    scope: CoroutineScope,
    settingStore: SettingStore = AppBrowserConfig.settingStore,
) {
    Column(modifier = Modifier.padding(16.dp)) {
        val itemModifier = Modifier.padding(bottom = 16.dp)

        val (gridSizeState, setGridSizeState) = SettingRowState(
            name = "Grid Size",
            value = runBlocking { settingStore.get(CanvasSetting.GridSize) },
            errorMessage = null
        ).asState()

        SettingRow(
            state = gridSizeState,
            extraContent = { Text("Dp") },
            modifier = itemModifier
        ) {
            val processedValue = try {
                it.toInt()
            } catch (e: Exception) {
                0
            }
            val isSuccess = processedValue > 0
            setGridSizeState(
                gridSizeState.copy(
                    value = processedValue,
                    errorMessage = if (isSuccess) null else "Invalid number"
                )
            )

            if (isSuccess) {
                scope.launch(Dispatchers.IO) {
                    settingStore.put(CanvasSetting.GridSize, processedValue)
                }
            }
        }

        val (gridColorState, setGridColorState) = SettingRowState(
            name = "Grid Color",
            value = runBlocking { settingStore.get(CanvasSetting.GridColor) },
            errorMessage = null
        ).asState()

        SettingRow(
            state = gridColorState,
            extraContent = { ColorBox(gridColorState.value.toColor()) },
            modifier = itemModifier
        ) { newValue ->
            val errorMessage = if (newValue.length <= 1) "Invalid value" else null
            setGridColorState(gridColorState.copy(value = newValue, errorMessage = errorMessage))

            val isSuccess = errorMessage == null
            if (isSuccess) {
                scope.launch(Dispatchers.IO) {
                    settingStore.put(CanvasSetting.GridColor, newValue)
                }
            }
        }

        val (canvasColorState, setCanvasColorState) = SettingRowState(
            name = "Canvas Color",
            value = runBlocking { settingStore.get(CanvasSetting.CanvasColor) },
            errorMessage = null
        ).asState()

        SettingRow(
            state = canvasColorState,
            extraContent = { ColorBox(canvasColorState.value.toColor()) },
            modifier = itemModifier
        ) { newValue ->
            val errorMessage = if (newValue.length <= 1) "Invalid value" else null
            setCanvasColorState(canvasColorState.copy(value = newValue, errorMessage = errorMessage))

            val isSuccess = errorMessage == null
            if (isSuccess) {
                scope.launch(Dispatchers.IO) {
                    settingStore.put(CanvasSetting.CanvasColor, newValue)
                }
            }
        }
    }
}

@Composable
private fun SettingRow(
    state: SettingRowState<*>,
    extraContent: (@Composable () -> Unit)? = null,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
) {
    val name = state.name
    val value = state.value
    val errorMessage = state.errorMessage

    Column(modifier = modifier) {
        Row(
            modifier = Modifier.padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                name,
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            )

            BasicTextField(
                value = value.toString(),
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
            extraContent?.invoke()
        }

        if (errorMessage != null) {
            Text(
                text = errorMessage,
                color = Color.Red.copy(alpha = 0.5f),
                fontSize = MaterialTheme.typography.caption.fontSize
            )
        }
    }
}

@Composable
private fun ColorBox(color: Color) {
    Box(
        modifier = Modifier
            .size(16.dp)
            .background(color)
    )
}
