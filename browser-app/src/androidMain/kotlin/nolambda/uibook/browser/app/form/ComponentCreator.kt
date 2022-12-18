package nolambda.uibook.browser.app.form

import android.content.Context
import nolambda.uibook.browser.app.measurement.MeasurementHelper
import nolambda.uibook.browser.app.measurement.MeasurementOverlayView

internal class ComponentCreator(
    private val context: Context
) {

    fun createMeasurementView(helper: MeasurementHelper): MeasurementOverlayView {
        return MeasurementOverlayView(context).apply {
            setMeasurementHelper(helper)
        }
    }
}
