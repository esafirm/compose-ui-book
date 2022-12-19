package nolambda.uibook.components.bookform

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.FullscreenExit
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
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
import kotlinx.coroutines.delay
import nolambda.uibook.annotations.BookMetaData
import nolambda.uibook.browser.config.AppBrowserConfig
import nolambda.uibook.browser.form.ComposeEmitter
import nolambda.uibook.components.UIBookColors
import nolambda.uibook.frame.Device
import nolambda.uibook.frame.DeviceFrame
import nolambda.uibook.frame.Devices

private const val LARGE_SCREEN_THRESHOLD = 1000

@Composable
private fun InputView(
    metaData: BookMetaData,
    inputData: InputData,
) {
    val inputCreators = inputData.inputCreators
    val viewState = inputData.viewState
    val setViewState = inputData.setViewState

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        metaData.parameters.forEachIndexed { index, parameter ->

            inputCreators[index].CreateInput(
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
    rawSourceCode: String,
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

    val showClipboardState = remember { mutableStateOf(false) }

    Box {
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

        OutlinedButton(
            border = BorderStroke(1.dp, Color.Gray),
            onClick = {
                AppBrowserConfig.clipboardManager.copyToClipboard(functionCode)
                showClipboardState.value = true
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .alpha(0.8f)
        ) {
            Icon(
                imageVector = Icons.Default.ContentCopy,
                contentDescription = "Copy to clipboard",
                tint = Color.Gray
            )
        }

        NotificationBar(
            message = "Copied to clipboard",
            showState = showClipboardState,
            modifier = Modifier.align(Alignment.BottomCenter)
                .padding(bottom = 24.dp)
        )
    }
}

@Composable
private fun NotificationBar(
    message: String,
    timeoutInMs: Long = 1_200,
    showState: MutableState<Boolean>,
    modifier: Modifier,
) {
    AnimatedVisibility(showState.value, modifier = modifier) {

        LaunchedEffect(Unit) {
            delay(timeoutInMs)
            showState.value = false
        }

        Box(
            modifier = Modifier.clip(RoundedCornerShape(8.dp))
                .background(UIBookColors.GREEN_SUCCESS)
        ) {
            Text(
                text = message,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
            )
        }
    }
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
private fun BoxScope.DeviceFrameWrapper(
    selectedDevice: Device,
    scale: Float,
    transformableState: TransformableState,
    book: ComposeEmitter,
) {
    if (selectedDevice == Devices.responsive) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
        ) {
            book()
        }
        return
    }

    val modifier = Modifier.align(Alignment.Center)
        .graphicsLayer(
            scaleX = scale,
            scaleY = scale,
        )
        .transformable(transformableState)
        .border(1.dp, Color.Gray, RectangleShape)

    DeviceFrame(selectedDevice, modifier = modifier) {
        book()
    }
}

@Composable
private fun ControlPane(
    meta: BookMetaData,
    inputData: InputData,
    modifier: Modifier = Modifier,
    selectedPaneIndex: Int,
    onSetSelectedPane: (Int) -> Unit,
) {
    Column(modifier = modifier) {
        TabView(
            titles = listOf("Source Code".uppercase(), "Modifier".uppercase()),
            selectedIndex = selectedPaneIndex
        ) { currentIndex ->
            onSetSelectedPane(currentIndex)
        }

        if (selectedPaneIndex == 0) {
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
    content: @Composable (Modifier) -> Unit,
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

/**
 * Represent the canvas to draw the book preview
 */
@Composable
private fun BookCanvas(
    modifier: Modifier = Modifier,
    selectedDevice: Device,
    scale: Float,
    transformableState: TransformableState,
    bookView: ComposeEmitter,
) {
    Box(modifier = modifier) {
        PixelGrid()
        DeviceFrameWrapper(
            selectedDevice = selectedDevice,
            scale = scale,
            transformableState = transformableState,
            bookView
        )

        OutlinedButton(
            onClick = GlobalState.fullScreenMode::toggle,
            border = BorderStroke(1.dp, Color.Gray),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .alpha(0.8f)
        ) {
            Icon(
                imageVector = if (GlobalState.fullScreenMode.value) {
                    Icons.Default.FullscreenExit
                } else {
                    Icons.Default.Fullscreen
                },
                contentDescription = if (GlobalState.fullScreenMode.value) {
                    "Exit full screen mode"
                } else {
                    "Enter full screen mode"
                },
                tint = Color.Gray
            )
        }
    }
}

/**
 * BookForm is a combination of [BookCanvas] and [ControlPane] of UI Book
 * As utility, it also contains a toolbar that contains needed actions for the book
 */
@Composable
fun BookForm(
    meta: BookMetaData,
    bookView: ComposeEmitter,
    inputData: InputData,
) {
    val selectedDevice = remember { mutableStateOf(Devices.responsive) }
    val isMeasurementEnabled = GlobalState.measurementEnabled

    val (scale, setScale) = remember { mutableStateOf(1f) }
    val state = rememberTransformableState { zoomChange, _, _ ->
        setScale(scale * zoomChange)
    }

    val (selectedPaneIndex, setSelectedPaneIndex) = remember { mutableStateOf(0) }

    Column {
        AnimatedVisibility(
            visible = GlobalState.fullScreenMode.invertedValue,
            modifier = Modifier.zIndex(1F)
        ) {
            FormToolbar(
                name = meta.name,
                isMeasurementEnabled = isMeasurementEnabled.value,
                selectedDevice = selectedDevice.value,
                scale = scale,
                onDeviceSelected = selectedDevice::value::set,
                onToggleClick = { isMeasurementEnabled.value = isMeasurementEnabled.value.not() },
                onScaleChange = setScale
            )
        }

        AdaptivePane(
            largeScreenThreshold = LARGE_SCREEN_THRESHOLD,
            modifier = Modifier.zIndex(0F),
            columnModifier = { Modifier.weight(1F) },
            rowModifier = { Modifier.weight(1F) }
        ) { adaptiveModifier ->

            BookCanvas(
                modifier = adaptiveModifier.composed { zIndex(0F) },
                selectedDevice = selectedDevice.value,
                scale = scale,
                transformableState = state,
                bookView = bookView
            )

            AnimatedVisibility(
                visible = GlobalState.fullScreenMode.invertedValue,
                modifier = adaptiveModifier.composed {
                    Modifier.background(MaterialTheme.colors.background)
                        .zIndex(1F)
                }
            ) {
                ControlPane(
                    meta = meta,
                    inputData = inputData,
                    selectedPaneIndex = selectedPaneIndex,
                    onSetSelectedPane = setSelectedPaneIndex
                )
            }
        }
    }
}
