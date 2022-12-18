package nolambda.uibook.browser.app.measurement

import android.graphics.Rect
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup

class MeasurementHelperImpl(private val root: ViewGroup) : MeasurementHelper {

    private val outLocations = IntArray(2)

    private val displayMetrics: DisplayMetrics = root.resources.displayMetrics

    private val rootY by lazy {
        val rootOutLocation = IntArray(2)
        root.getLocationOnScreen(rootOutLocation)
        rootOutLocation[1]
    }

    override fun getContentRoot(): ViewGroup = root

    override fun getContentRootLocation(view: View, rect: Rect) {

        view.getLocationOnScreen(outLocations)
        rect.left = outLocations[0]
        rect.top = outLocations[1] - rootY
        rect.right = rect.left + view.measuredWidth
        rect.bottom = rect.top + view.measuredHeight
    }

    override fun toDp(px: Int): Int {
        return (px / (displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
    }
}
