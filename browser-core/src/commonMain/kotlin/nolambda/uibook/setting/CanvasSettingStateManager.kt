package nolambda.uibook.setting

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import nolambda.uibook.browser.config.CanvasSetting
import nolambda.uibook.browser.config.SettingStore

/**
 * State Manager for [CanvasSetting] composable
 */
internal class CanvasSettingStateManager(
    private val scope: CoroutineScope,
    private val settingStore: SettingStore,
) {
    val gridSize = MutableStateFlow(
        SettingRowData(
            name = "Grid Size",
            value = CanvasSetting.GridSize.defaultValue,
            errorMessage = null
        )
    )

    val gridColor = MutableStateFlow(
        SettingRowData(
            name = "Grid Color",
            value = CanvasSetting.GridColor.defaultValue,
            errorMessage = null
        )
    )

    val canvasColor = MutableStateFlow(
        SettingRowData(
            name = "Canvas Color",
            value = CanvasSetting.CanvasColor.defaultValue,
            errorMessage = null
        )
    )

    init {
        val job = scope.launch {
            launch {
                val result = settingStore.get(CanvasSetting.GridSize)
                gridSize.value = gridSize.value.copy(value = result)
            }
            launch {
                val result = settingStore.get(CanvasSetting.GridColor)
                gridColor.value = gridColor.value.copy(value = result)
            }
            launch {
                val result = settingStore.get(CanvasSetting.CanvasColor)
                canvasColor.value = canvasColor.value.copy(value = result)
            }
        }
        job.invokeOnCompletion { throwable ->
            println("Invoke on completion: $throwable")
            throwable?.printStackTrace()
        }
    }

    fun setGridSize(data: SettingRowData<Int>) {
        gridSize.value = data

        if (data.isSuccess) {
            scope.launch {
                settingStore.put(CanvasSetting.GridSize, data.value)
            }
        }
    }

    fun setGridColor(data: SettingRowData<String>) {
        gridColor.value = data

        if (data.isSuccess) {
            scope.launch {
                settingStore.put(CanvasSetting.GridColor, data.value)
            }
        }
    }

    fun setCanvasColor(data: SettingRowData<String>) {
        canvasColor.value = data

        if (data.isSuccess) {
            scope.launch {
                settingStore.put(CanvasSetting.GridColor, data.value)
            }
        }
    }
}
