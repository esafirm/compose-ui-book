package nolambda.uibook.setting

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

internal data class SettingRowState<T>(
    val name: String,
    val value: T,
    val errorMessage: String? = null,
)

@Composable
internal fun <T> SettingRowState<T>.asState() = remember { mutableStateOf(this) }
