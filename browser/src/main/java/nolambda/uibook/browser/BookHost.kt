package nolambda.uibook.browser

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup

class BookHost(
    val context: Context,
    val parent: ViewGroup
) {
    val inflater by lazy(LazyThreadSafetyMode.NONE) {
        LayoutInflater.from(context)
    }
}