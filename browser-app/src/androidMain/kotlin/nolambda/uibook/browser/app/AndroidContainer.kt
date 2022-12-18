package nolambda.uibook.browser.app

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import nolambda.uibook.browser.app.form.ComponentCreator
import nolambda.uibook.browser.app.measurement.MeasurementHelperImpl
import nolambda.uibook.components.bookform.GlobalState

/**
 * A container to bridge pure compose with Android's View
 * The placement of this is managed in the processor/generator itself
 */
@Composable
fun AndroidContainer(
    bookView: View
) {
    val context = LocalContext.current
    val isMeasurementEnabled = GlobalState.measurementEnabled

    Box(
        modifier = Modifier
            .background(Color.Cyan)
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        AndroidView(
            factory = {
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
            },
            modifier = Modifier.align(Alignment.Center),
            update = { outerView ->
                val container = (outerView as ViewGroup).getChildAt(1) as FrameLayout
                val child = container.getChildAt(0)

                if (child != bookView) {
                    container.removeView(child)
                    container.addView(bookView)
                }

                val measurementView = outerView.getChildAt(0)
                measurementView.visibility = if (isMeasurementEnabled.value) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            }
        )
    }
}

private fun ViewGroup.addMeasurementHelper() {
    val componentCreator = ComponentCreator(context)
    val measurementHelper = MeasurementHelperImpl(this)
    val measurementView = componentCreator.createMeasurementView(measurementHelper)
    addView(measurementView)
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
