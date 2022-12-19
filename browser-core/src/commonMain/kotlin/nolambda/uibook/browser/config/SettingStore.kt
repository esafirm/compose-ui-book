package nolambda.uibook.browser.config

import io.github.irgaly.kottage.KottageStorage
import kotlin.reflect.KType
import kotlin.reflect.typeOf

interface SettingStore {
    suspend fun <T : Any> put(key: String, value: T, type: KType)
    suspend fun <T : Any> get(key: String, type: KType): T?
}

class KottageSettingStore(
    private val internalStore: KottageStorage,
) : SettingStore {
    override suspend fun <T : Any> put(key: String, value: T, type: KType) {
        internalStore.put(key, value, type)
    }

    override suspend fun <T : Any> get(key: String, type: KType): T? {
        return internalStore.get(key, type)
    }
}

suspend inline fun <reified T : Any> SettingStore.put(key: String, value: String) = put(key, value, typeOf<T>())
suspend inline fun <reified T : Any> SettingStore.get(key: String): T? = get(key, typeOf<T>())
