package nolambda.uibook.sample.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import nolambda.uibook.annotations.State
import nolambda.uibook.annotations.UIBook
import nolambda.uibook.browser.BookHost
import nolambda.uibook.browser.form.OptionInputCreator
import nolambda.uibook.browser.form.SliderInputCreator

internal class SimpleItem : OptionInputCreator<String>(listOf("A", "B", "C"))
internal class SimpleIntSlider : SliderInputCreator(steps = 10, valueRange = 0f..10f)

@UIBook(name = "Sample compose code")
@Composable
fun BookHost.SampleText(
    @State(defaultValue = "true") testFlag: Boolean,
    @State(defaultValue = "A", inputCreator = SimpleItem::class) switch: String,
    @State(defaultValue = "0", inputCreator = SimpleIntSlider::class) slider: Int,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text("Flag value: $testFlag")
        Text("Switch value: $switch")
        Text("Slider value: $slider")
    }
}
