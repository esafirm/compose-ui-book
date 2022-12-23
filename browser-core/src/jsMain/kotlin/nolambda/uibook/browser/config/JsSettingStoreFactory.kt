package nolambda.uibook.browser.config

import io.github.irgaly.kottage.Kottage
import io.github.irgaly.kottage.KottageEnvironment
import io.github.irgaly.kottage.platform.KottageContext
import kotlinx.serialization.json.Json

object JsSettingStoreFactory {
    fun createStore(): SettingStore {
        // Initialize with Kottage database information.
        val kottage = Kottage(
            name = "uibook-ks-store", // This will be database file name
            directoryPath = "/tmp/",
            environment = KottageEnvironment(KottageContext()),
            json = Json
        )

        return KottageSettingStore(
            internalStore = kottage.storage("desktop-setting-store")
        )
    }
}
