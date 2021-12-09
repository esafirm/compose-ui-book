package nolambda.uibook.factory

import android.app.Activity
import android.content.Context

sealed class Host
class ActivityHost(val activity: Activity) : Host()

data class BookConfig(
    val host: Host,
    val onExit: () -> Unit
) {
    fun context(): Context {
        return when (host) {
            is ActivityHost -> host.activity
        }
    }
}