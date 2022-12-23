package nolambda.uibook.components.bookform

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nolambda.uibook.browser.config.AppBrowserConfig
import nolambda.uibook.browser.config.ResourceIds
import nolambda.uibook.frame.Device
import nolambda.uibook.frame.Devices
import nolambda.uibook.icons.Devices
import nolambda.uibook.icons.UIBookIcons
import kotlin.math.max
import kotlin.math.min

@Composable
internal fun FormToolbar(
    modifier: Modifier = Modifier,
    name: String,
    isMeasurementEnabled: Boolean,
    selectedDevice: Device,
    scale: Float,
    onScaleChange: (scale: Float) -> Unit,
    onDeviceSelected: (Device) -> Unit,
    onToggleClick: () -> Unit,
) {
    val expandDeviceSelector = remember { mutableStateOf(false) }

    TopAppBar(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = name, fontSize = 18.sp, modifier = Modifier.padding(16.dp))

            Row {
                if (selectedDevice != Devices.responsive) {
                    ScaleCounter(
                        currentScale = scale,
                        onScaleChange = onScaleChange
                    )
                }

                DeviceFrameSelector(
                    selectedDevice = selectedDevice,
                    onDeviceSelected = onDeviceSelected,
                    isExpanded = expandDeviceSelector.value,
                    onExpand = expandDeviceSelector::value::set
                )
                Spacer(Modifier.width(16.dp))

                val isMeasurementFeatureEnabled = AppBrowserConfig.browserFeatures.measurementOverlay
                if (isMeasurementFeatureEnabled) {
                    ButtonToggleMeasurement(isMeasurementEnabled, onToggleClick)
                }

            }
        }
    }
}

@Composable
private fun ButtonToggleMeasurement(
    isMeasurementEnabled: Boolean,
    onToggleClick: () -> Unit,
) {
    val resourceLoader = AppBrowserConfig.resourceLoader

    val id = if (isMeasurementEnabled) {
        ResourceIds.MEASUREMENT_ENABLED
    } else {
        ResourceIds.MEASUREMENT_DISABLED
    }
    val image = resourceLoader.load(id)

    Button(onClick = onToggleClick) {
        Image(painter = image, contentDescription = "Toggle Measurement")
    }
}

@Composable
private fun DeviceFrameSelector(
    isExpanded: Boolean,
    selectedDevice: Device,
    onDeviceSelected: (Device) -> Unit,
    onExpand: (Boolean) -> Unit,
) {
    Row {
        Button(
            onClick = { onExpand(true) }
        ) {
            Icon(
                imageVector = UIBookIcons.Devices,
                contentDescription = "Device Frame Selector"
            )
        }

        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { onExpand(false) },
            modifier = Modifier.wrapContentWidth()
        ) {
            Devices.all.forEachIndexed { index, device ->
                DropdownMenuItem(onClick = {
                    onDeviceSelected(Devices.all[index])
                    onExpand(false)
                }) {
                    if (device == selectedDevice) {
                        Text(text = device.name, fontWeight = FontWeight.Bold)
                    } else {
                        Text(text = device.name)
                    }
                }
            }
        }
    }
}

@Composable
private fun ScaleCounter(
    currentScale: Float,
    onScaleChange: (scale: Float) -> Unit,
) {
    Row {
        Button(onClick = {
            onScaleChange(max(0.1f, currentScale - 0.1f))
        }) {
            Text(text = "-")
        }
        Text(
            text = currentScale.toString().substring(0, 3),
            color = MaterialTheme.typography.subtitle1.color,
            modifier = Modifier.align(Alignment.CenterVertically)
                .padding(horizontal = 8.dp)
        )
        Button(onClick = {
            onScaleChange(min(2f, currentScale + 0.1f))
        }) {
            Text(text = "+")
        }
    }
}
