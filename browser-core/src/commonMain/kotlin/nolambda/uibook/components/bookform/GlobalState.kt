package nolambda.uibook.components.bookform

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf

object GlobalState {
    val measurementEnabled = mutableStateOf(false)
    val fullScreenMode = BooleanStateHolder(mutableStateOf(false))
}


class BooleanStateHolder(
    private val state: MutableState<Boolean>,
) {
    fun toggle() {
        state.value = !state.value
    }

    fun setValue(value: Boolean) {
        state.value = value
    }

    val value get() = state.value
    val invertedValue get() = !state.value
}
