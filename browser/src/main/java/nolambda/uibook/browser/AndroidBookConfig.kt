package nolambda.uibook.browser

import android.app.Activity
import android.content.Context
import nolambda.uibook.factory.BookConfig

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
