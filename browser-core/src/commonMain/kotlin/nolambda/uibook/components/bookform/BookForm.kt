package nolambda.uibook.components.bookform

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wakaztahir.codeeditor.model.CodeLang
import com.wakaztahir.codeeditor.prettify.PrettifyParser
import com.wakaztahir.codeeditor.theme.CodeThemeType
import com.wakaztahir.codeeditor.utils.parseCodeAsAnnotatedString
import nolambda.uibook.annotations.BookMetaData
import nolambda.uibook.browser.form.ComposeEmitter
import nolambda.uibook.components.UIBookColors
import nolambda.uibook.frame.Device
import nolambda.uibook.frame.Devices

@Composable
private fun InputView(
    metaData: BookMetaData,
    inputData: InputData
) {
    val inputCreator = inputData.inputCreator
    val viewState = inputData.viewState
    val setViewState = inputData.setViewState

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        metaData.parameters.forEachIndexed { index, parameter ->

            inputCreator.CreateInput(
                parameter = parameter,
                defaultState = viewState[index],
                setViewState = { newViewState -> setViewState(index, newViewState) },
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}


@Composable
private fun SourceCodeView(
    rawSourceCode: String
) {
    val verticalScrollState = rememberScrollState()
    val horizontalScrollState = rememberScrollState()

    val functionCode = remember {
        rawSourceCode.replace("return ", "")
            .removeSurrounding("{", "}")
            .trimIndent()
    }

    val parser = remember { PrettifyParser() }
    val theme = remember { CodeThemeType.Monokai.theme }

    val parsedCode = remember {
        parseCodeAsAnnotatedString(
            parser = parser,
            theme = theme,
            lang = CodeLang.Java,
            code = functionCode
        )
    }

    Text(
        parsedCode,
        fontFamily = FontFamily.Monospace,
        modifier = Modifier
            .fillMaxSize()
            .background(UIBookColors.MONOKAI)
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)
            .verticalScroll(verticalScrollState)
            .horizontalScroll(horizontalScrollState)
    )
}

@Composable
private fun TabView(
    titles: List<String>,
    selectedIndex: Int = 0,
    onClick: (Int) -> Unit,
) {
    Row(modifier = Modifier.shadow(1.dp)) {
        titles.forEachIndexed { index, title ->

            val isSelected = selectedIndex == index
            val textColor = if (isSelected) UIBookColors.PURPLE_700 else Color.Black

            Box(modifier = Modifier.weight(1F)) {
                Text(
                    text = title,
                    color = textColor,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .rippleClick { onClick(index) }
                        .padding(16.dp)
                )

                if (isSelected) {
                    Box(
                        modifier = Modifier
                            .align(alignment = Alignment.BottomCenter)
                            .fillMaxWidth()
                            .height(2.dp)
                            .clip(RectangleShape)
                            .background(textColor)
                    )
                }
            }
        }
    }
}

@Composable
private fun Modifier.rippleClick(onClick: () -> Unit): Modifier {
    return clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = rememberRipple(),
        onClick = onClick
    )
}

@Composable
private fun BoxScope.DeviceFrame(
    selectedDevice: Device,
    book: ComposeEmitter
) {
    val originalModifier = Modifier.align(Alignment.Center)
    val finalModifier = if (selectedDevice != Devices.responsive) {
        originalModifier
            .width(selectedDevice.resolution.logicalDevice.width.dp)
            .height(selectedDevice.resolution.logicalDevice.height.dp)
            .scale(selectedDevice.resolution.scaleFactor)
            .border(1.dp, Color.Gray, RectangleShape)
            .background(Color.White)
    } else originalModifier

    Box(Modifier.fillMaxSize()) {
        Box(finalModifier) {
            book()
        }
    }
}

@Composable
fun BookForm(
    meta: BookMetaData,
    bookView: ComposeEmitter,
    inputData: InputData
) {

    val selectedIndex = remember { mutableStateOf(0) }
    val selectedDevice = remember { mutableStateOf(Devices.responsive) }
    val isMeasurementEnabled = GlobalState.measurementEnabled

    Column {
        FormToolbar(
            name = meta.name,
            isMeasurementEnabled = isMeasurementEnabled.value,
            selectedDevice = selectedDevice.value,
            onDeviceSelected = selectedDevice::value::set,
            onToggleClick = { isMeasurementEnabled.value = isMeasurementEnabled.value.not() },
            onScaleChange = {
                val currentDevice = selectedDevice.value
                selectedDevice.value = currentDevice.copy(
                    resolution = currentDevice.resolution.copy(scaleFactor = it)
                )
            }
        )

        Row {
            Box(
                modifier = Modifier.weight(1F)
            ) {
                PixelGrid()
                DeviceFrame(selectedDevice.value, bookView)
            }

            Column(modifier = Modifier.width(560.dp)) {
                TabView(
                    titles = listOf("Source Code".uppercase(), "Modifier".uppercase()),
                    selectedIndex = selectedIndex.value
                ) {
                    selectedIndex.value = it
                }

                if (selectedIndex.value == 0) {
                    key(meta.name) {
                        SourceCodeView(rawSourceCode = meta.function)
                    }
                } else {
                    key(meta.name) {
                        InputView(metaData = meta, inputData = inputData)
                    }
                }
            }
        }
    }
}
