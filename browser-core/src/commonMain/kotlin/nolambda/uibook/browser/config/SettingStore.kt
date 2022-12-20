package nolambda.uibook.browser.config

import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import io.github.irgaly.kottage.KottageStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.reflect.KType
import kotlin.reflect.typeOf

interface SettingStore {
    suspend fun <T : Any> put(setting: Setting<T>, value: T)
    suspend fun <T : Any> get(setting: Setting<T>): T

    fun <T : Any> observe(scope: CoroutineScope, setting: Setting<T>): Flow<T>
}

class Setting<T : Any>(
    val key: String,
    val type: KType,
    val defaultValue: T,
) {
    companion object {
        inline fun <reified T : Any> create(
            key: String,
            defaultValue: T,
        ): Setting<T> {
            return Setting(key, typeOf<T>(), defaultValue)
        }
    }
}

object CanvasSetting {
    val GridSize = Setting.create("grid_size", 8)
    val GridColor = Setting.create("grid_color", "#000000")
    val CanvasColor = Setting.create("canvas_color", "#FFFFFF")
}

internal class KottageSettingStore(
    private val internalStore: KottageStorage,
) : SettingStore {

    override suspend fun <T : Any> put(setting: Setting<T>, value: T) {
        internalStore.put(setting.key, value, setting.type)
    }

    override suspend fun <T : Any> get(setting: Setting<T>): T {
        return try {
            internalStore.get(setting.key, setting.type)
        } catch (e: NoSuchElementException) {
            setting.defaultValue
        }
    }

    override fun <T : Any> observe(scope: CoroutineScope, setting: Setting<T>): Flow<T> {
        val mutableState = MutableStateFlow(setting.defaultValue)

        scope.launch {
            // Initial value
            mutableState.emit(get(setting))
        }

        scope.launch {
            internalStore.eventFlow().collect {
                if (it.itemKey == setting.key) {
                    mutableState.emit(get(setting))
                }
            }
        }
        return mutableState
    }
}

@Composable
fun <T : Any> SettingStore.observeAsState(
    scope: CoroutineScope,
    setting: Setting<T>,
) = produceState(initialValue = setting.defaultValue) {
    observe(scope, setting).collect {
        value = it
    }
}
