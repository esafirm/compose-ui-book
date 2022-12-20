package nolambda.uibook.browser.config

import android.content.Context
import io.github.irgaly.kottage.Kottage
import io.github.irgaly.kottage.KottageEnvironment
import io.github.irgaly.kottage.platform.contextOf
import kotlinx.serialization.json.Json

object AndroidSettingStoreFactory {

    fun createStore(context: Context): SettingStore {
        val databaseDirectory: String = context.cacheDir.path
        val kottageEnvironment = KottageEnvironment(
            context = contextOf(context)
        )

        // Initialize with Kottage database information.
        val kottage = Kottage(
            name = "uibook-ks-store", // This will be database file name
            directoryPath = databaseDirectory,
            environment = kottageEnvironment,
            json = Json.Default
        )

        return KottageSettingStore(
            internalStore = kottage.storage("android-setting-store")
        )
    }
}

