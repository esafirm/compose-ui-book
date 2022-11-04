package nolambda.uibook.browser.form

import android.content.Context
import nolambda.uibook.browser.measurement.MeasurementHelper
import nolambda.uibook.browser.measurement.MeasurementOverlayView

internal class ComponentCreator(
    private val context: Context
) {

    fun createMeasurementView(helper: MeasurementHelper): MeasurementOverlayView {
        return MeasurementOverlayView(context).apply {
            setMeasurementHelper(helper)
        }
    }
}