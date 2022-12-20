package nolambda.uibook.browser.config

import io.github.irgaly.kottage.Kottage
import io.github.irgaly.kottage.KottageEnvironment
import io.github.irgaly.kottage.platform.KottageContext
import kotlinx.serialization.json.Json
import java.io.File

object DesktopSettingStoreFactory {
    fun createStore(
        path: String = "${System.getProperty("user.home")}/.uibook/store/",
    ): SettingStore {

        // Make sure it exists
        File(path).mkdirs()

        // Initialize with Kottage database information.
        val kottage = Kottage(
            name = "uibook-ks-store", // This will be database file name
            directoryPath = path,
            environment = KottageEnvironment(KottageContext()),
            json = Json
        )

        return KottageSettingStore(
            internalStore = kottage.storage("desktop-setting-store")
        )
    }
}
