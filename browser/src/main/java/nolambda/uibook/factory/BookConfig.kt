package nolambda.uibook.factory

import android.app.Activity
import android.content.Context

sealed class Host
class ActivityHost(val activity: Activity) : Host()

data class AndroidBookConfig(
    val host: Host,
    override val onExit: () -> Unit
) : BookConfig {

    fun context(): Context {
        return when (host) {
            is ActivityHost -> host.activity
        }
    }
}
