package nolambda.uibook.components.bookform

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.TransformableState
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.wakaztahir.codeeditor.model.CodeLang
import com.wakaztahir.codeeditor.prettify.PrettifyParser
import com.wakaztahir.codeeditor.theme.CodeThemeType
import com.wakaztahir.codeeditor.utils.parseCodeAsAnnotatedString
import nolambda.uibook.annotations.BookMetaData
import nolambda.uibook.browser.form.ComposeEmitter
import nolambda.uibook.components.UIBookColors
import nolambda.uibook.frame.Device
import nolambda.uibook.frame.Devices

private const val LARGE_SCREEN_THRESHOLD = 1000

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
    scale: Float,
    transformableState: TransformableState,
    book: ComposeEmitter
) {
    val originalModifier = Modifier.align(Alignment.Center)
    val finalModifier = if (selectedDevice != Devices.responsive) {
        originalModifier
            .width(selectedDevice.resolution.nativeSize.width.dp)
            .height(selectedDevice.resolution.nativeSize.height.dp)
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale,
            )
            .transformable(transformableState)
            .background(Color.White)
            .border(1.dp, Color.Gray, RectangleShape)
    } else originalModifier

    Box(Modifier.fillMaxSize()) {
        Box(finalModifier) {
            book()
        }
    }
}

@Composable
private fun ControlPane(
    meta: BookMetaData,
    inputData: InputData,
    modifier: Modifier = Modifier
) {
    val selectedIndex = remember { mutableStateOf(0) }

    Column(modifier = modifier) {
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

/**
 * Layout that changing between row and column based on the screen size
 */
@Suppress("SameParameterValue")
@Composable
private fun AdaptivePane(
    largeScreenThreshold: Int,
    modifier: Modifier = Modifier,
    columnModifier: @Composable ColumnScope.() -> Modifier,
    rowModifier: @Composable RowScope.() -> Modifier,
    content: @Composable (Modifier) -> Unit
) {
    BoxWithConstraints(modifier = modifier) {
        val isLargeScreen = maxWidth > largeScreenThreshold.dp
        if (isLargeScreen) {
            Row {
                content(rowModifier())
            }
        } else {
            Column {
                content(columnModifier())
            }
        }
    }
}

@Composable
fun BookForm(
    meta: BookMetaData,
    bookView: ComposeEmitter,
    inputData: InputData
) {
    val selectedDevice = remember { mutableStateOf(Devices.responsive) }
    val isMeasurementEnabled = GlobalState.measurementEnabled

    val (scale, setScale) = remember { mutableStateOf(1f) }
    val state = rememberTransformableState { zoomChange, _, _ ->
        setScale(scale * zoomChange)
    }

    Column {
        FormToolbar(
            name = meta.name,
            isMeasurementEnabled = isMeasurementEnabled.value,
            selectedDevice = selectedDevice.value,
            scale = scale,
            onDeviceSelected = selectedDevice::value::set,
            onToggleClick = { isMeasurementEnabled.value = isMeasurementEnabled.value.not() },
            onScaleChange = setScale,
            modifier = Modifier.zIndex(1F)
        )

        AdaptivePane(
            largeScreenThreshold = LARGE_SCREEN_THRESHOLD,
            modifier = Modifier.zIndex(0F),
            columnModifier = { Modifier.weight(1F) },
            rowModifier = { Modifier.weight(1F) }
        ) { adaptiveModifier ->

            Row(
                modifier = Modifier.weight(1F).zIndex(0F)
            ) {
                Box {
                    PixelGrid()
                    DeviceFrame(
                        selectedDevice = selectedDevice.value,
                        scale = scale,
                        transformableState = state,
                        bookView
                    )
                }
            }

            ControlPane(
                meta = meta,
                inputData = inputData,
                modifier = adaptiveModifier.composed {
                    Modifier.background(MaterialTheme.colors.background)
                        .zIndex(1F)
                }
            )
        }
    }
}
