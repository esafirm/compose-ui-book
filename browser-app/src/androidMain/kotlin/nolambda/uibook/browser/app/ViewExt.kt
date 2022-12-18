package nolambda.uibook.browser.app

import android.view.View

fun View.show(isVisible: Boolean, animate: Boolean = false) {
    if (isVisible && visibility == View.VISIBLE) return
    if (!isVisible && visibility == View.GONE) return
    if (animate) {
        val alpha = if (isVisible) 1f else 0f
        animate().alpha(alpha)
            .withStartAction {
                if (isVisible) {
                    visibility = View.VISIBLE
                }
            }
            .withEndAction {
                if (!isVisible) {
                    visibility = View.GONE
                }
            }
        return
    }
    visibility = if (isVisible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

fun View.setViewState(enabled: Boolean) {
    val alpha = if (enabled) 1F else 0.5F
    isEnabled = enabled
    setAlpha(alpha)
}
