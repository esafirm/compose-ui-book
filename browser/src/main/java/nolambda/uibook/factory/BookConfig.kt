package nolambda.uibook.factory

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment

sealed class Host
class ActivityHost(val activity: Activity) : Host()
class FragmentHost(val fragment: Fragment) : Host()

data class BookConfig(
    val host: Host,
    val onExit: () -> Unit
) {
    fun context(): Context {
        return when (host) {
            is ActivityHost -> host.activity
            is FragmentHost -> host.fragment.requireContext()
        }
    }
}