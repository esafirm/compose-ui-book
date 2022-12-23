package nolambda.uibook.setting

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

internal data class SettingRowData<T>(
    val name: String,
    val value: T,
    val errorMessage: String? = null,
) {
    val isSuccess get() = errorMessage == null
}

@Composable
internal fun <T> SettingRowData<T>.asState() = remember { mutableStateOf(this) }
