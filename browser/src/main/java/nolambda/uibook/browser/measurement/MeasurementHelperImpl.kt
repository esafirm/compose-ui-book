package nolambda.uibook.browser.measurement

import android.graphics.Rect
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup

class MeasurementHelperImpl(
    private val root: ViewGroup,
    decorView: View? = null
) : MeasurementHelper {

    private val outLocations = IntArray(2)

    private val displayMetrics: DisplayMetrics = root.resources.displayMetrics

    private val statusBarHeight = if (decorView != null) {
        val rect = Rect()
        decorView.getWindowVisibleDisplayFrame(rect)
        rect.top
    } else {
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24F, displayMetrics).toInt()
    }

    override fun getContentRoot(): ViewGroup = root

    override fun getContentRootLocation(view: View, rect: Rect) {
        view.getLocationOnScreen(outLocations)

        rect.left = outLocations[0]
        rect.top = outLocations[1] - statusBarHeight
        rect.right = rect.left + view.measuredWidth
        rect.bottom = rect.top + view.measuredHeight
    }

    override fun toDp(px: Int): Int {
        return (px / (displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
    }
}