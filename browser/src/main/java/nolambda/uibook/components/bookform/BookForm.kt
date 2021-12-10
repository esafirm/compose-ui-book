package nolambda.uibook.components.bookform

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.wakaztahir.codeeditor.highlight.model.CodeLang
import com.wakaztahir.codeeditor.highlight.prettify.PrettifyParser
import com.wakaztahir.codeeditor.highlight.theme.CodeThemeType
import com.wakaztahir.codeeditor.highlight.utils.parseCodeAsAnnotatedString
import nolambda.uibook.annotations.BookMetaData
import nolambda.uibook.browser.R
import nolambda.uibook.browser.form.ComponentCreator
import nolambda.uibook.browser.measurement.MeasurementHelperImpl

@Composable
private fun Toolbar(
    name: String,
    isMeasurementEnabled: Boolean,
    onToggleClick: () -> Unit
) {
    TopAppBar {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = name, fontSize = 18.sp, modifier = Modifier.padding(16.dp))

            val resource = if (isMeasurementEnabled) {
                R.drawable.ic_measurement_enabled
            } else {
                R.drawable.ic_measurement_disabled
            }
            val image = painterResource(id = resource)

            Button(onClick = onToggleClick) {
                Image(painter = image, contentDescription = "Toggle Measurement")
            }
        }
    }
}

@Composable
private fun BoxScope.BookViewContainer(
    meta: BookMetaData,
    bookView: View,
    isMeasurementEnabled: Boolean
) {
    val context = LocalContext.current

    AndroidView(
        factory = {
            if (meta.isComposeFunction) {
                bookView
            } else {
                // Add container so it can be easily updated
                FrameLayout(context).apply {
                    layoutParams = fillMaxSize()

                    addMeasurementHelper()
                    addView(FrameLayout(context).apply {
                        layoutParams = wrapSize().apply {
                            gravity = Gravity.CENTER
                        }

                        addView(bookView)
                    })
                }
            }
        },
        modifier = Modifier.align(Alignment.Center),
        update = { outerView ->
            if (meta.isComposeFunction.not()) {

                val container = (outerView as ViewGroup).getChildAt(1) as FrameLayout
                val child = container.getChildAt(0)

                if (child != bookView) {
                    container.removeView(child)
                    container.addView(bookView)
                }

                val measurementView = outerView.getChildAt(0)
                measurementView.visibility = if (isMeasurementEnabled) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            }
        }
    )
}

private fun ViewGroup.fillMaxSize(): FrameLayout.LayoutParams {
    return FrameLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT
    )
}

private fun ViewGroup.wrapSize(): FrameLayout.LayoutParams {
    return FrameLayout.LayoutParams(
        ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT,
    )
}

private fun ViewGroup.addMeasurementHelper() {
    val componentCreator = ComponentCreator(context)
    val measurementHelper = MeasurementHelperImpl(this)
    val measurementView = componentCreator.createMeasurementView(measurementHelper)
    addView(measurementView)
}

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
    val theme = remember { CodeThemeType.Monokai.theme() }

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
            .background(colorResource(id = R.color.monokai_background))
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)
            .verticalScroll(verticalScrollState)
            .horizontalScroll(horizontalScrollState)
    )
}

@Preview
@Composable
private fun TabViewPreview() {
    Box(modifier = Modifier.background(Color.White)) {
        TabView(titles = listOf("TESTING", "BAAA"), onClick = {})
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
            val textColor = colorResource(id = if (isSelected) R.color.purple_700 else R.color.black)

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
fun BookForm(
    meta: BookMetaData,
    bookView: View,
    inputData: InputData
) {

    val selectedIndex = remember { mutableStateOf(0) }
    val isMeasurementEnabled = remember { mutableStateOf(true) }

    Column {
        Toolbar(name = meta.name, isMeasurementEnabled = isMeasurementEnabled.value) {
            isMeasurementEnabled.value = isMeasurementEnabled.value.not()
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F)
        ) {
            PixelGrid()
            BookViewContainer(meta = meta, bookView = bookView, isMeasurementEnabled = isMeasurementEnabled.value)
        }

        Column(modifier = Modifier.weight(1F)) {
            TabView(
                titles = listOf("Source Code".uppercase(), "Modifier".uppercase()),
                selectedIndex = selectedIndex.value
            ) {
                selectedIndex.value = it
            }

            if (selectedIndex.value == 0) {
                SourceCodeView(rawSourceCode = meta.function)
            } else {
                InputView(metaData = meta, inputData = inputData)
            }
        }
    }
}