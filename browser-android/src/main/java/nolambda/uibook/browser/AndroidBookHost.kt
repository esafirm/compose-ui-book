package nolambda.uibook.browser

import android.content.Context
import android.view.LayoutInflater

class AndroidBookHost(
    val context: Context
) : BookHost {
    val inflater: LayoutInflater by lazy { LayoutInflater.from(context) }
}