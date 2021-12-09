package nolambda.uibook.browser

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup

class BookHost(
    val context: Context
) {
    val inflater: LayoutInflater by lazy(LazyThreadSafetyMode.NONE) {
        LayoutInflater.from(context)
    }
}