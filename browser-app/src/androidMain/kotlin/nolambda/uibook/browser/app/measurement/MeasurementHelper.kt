package nolambda.uibook.browser.app.measurement

import android.graphics.Rect
import android.view.View
import android.view.ViewGroup

interface MeasurementHelper {
    fun getContentRoot(): ViewGroup
    fun getContentRootLocation(view: View, rect: Rect)

    fun toDp(px: Int): Int
}
