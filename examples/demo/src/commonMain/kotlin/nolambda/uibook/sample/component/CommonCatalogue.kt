package nolambda.uibook.sample.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nolambda.uibook.annotations.State
import nolambda.uibook.annotations.UIBook
import nolambda.uibook.browser.BookHost
import nolambda.uibook.browser.form.OptionInputCreator
import nolambda.uibook.browser.form.SliderInputCreator
import nolambda.uibook.frame.DeviceFrame
import nolambda.uibook.frame.Devices

internal class SimpleItem : OptionInputCreator<String>(listOf("A", "B", "C"))
internal class SimpleIntSlider : SliderInputCreator(steps = 10, valueRange = 0f..10f)

@UIBook(name = "Compose code")
@Composable
fun BookHost.SampleText(
    @State(defaultValue = "", inputCreator = ButtonInput::class) text: String,
    @State(defaultValue = "true") testFlag: Boolean,
    @State(defaultValue = "A", inputCreator = SimpleItem::class) switch: String,
    @State(defaultValue = "0", inputCreator = SimpleIntSlider::class) slider: Int,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text("First value: $text")
        Text("Second value: $testFlag")
        Text("Third value: $switch")
        Text("Slider value: $slider")
    }
}

@UIBook(name = "Circular Image Sample")
@Composable
fun BookHost.CircularImage(
    @State(defaultValue = "https://silly-kowalevski-bc3c3d.netlify.app/logo.png") imageUrl: String,
    @State(defaultValue = "Dimsum Askitea") title: String,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        RemoteImage(
            url = imageUrl,
            contentDescription = "avatar",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Black, CircleShape)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            fontSize = 22.sp
        )
    }
}

@UIBook(name = "Overflow Sample")
@Composable
fun BookHost.OverflowSample(
    @State(defaultValue = "100") parentWidth: Int,
    @State(defaultValue = "200") childWidth: Int,
) {
    Box(
        modifier = Modifier
            .width(parentWidth.dp)
            .height(200.dp)
            .background(Color.Red)
    ) {
        Box(
            modifier = Modifier
                .requiredWidth(childWidth.dp)
                .height(100.dp)
                .background(Color.Blue)
        ) {
            Text("This blue box should have $childWidth dp width", color = Color.White)
        }
    }
}

@UIBook(name = "Scaffold Sample")
@Composable
fun BookHost.ScaffoldSample() {
    val (count, setCount) = remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar {
                Text("Top Bar", modifier = Modifier.padding(16.dp))
            }
        },
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                Text(
                    text = "Count: $count",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        },
        bottomBar = {
            BottomAppBar {
                Text("Bottom Bar", modifier = Modifier.padding(16.dp))
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                setCount(count + 1)
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        }
    )
}

internal class DeviceFrameSlider : SliderInputCreator(
    steps = Devices.all.size, valueRange = 0f..Devices.all.size.toFloat() - 1
)

@UIBook(name = "Generic Phone Frame")
@Composable
fun BookHost.GenericPhoneFrame(
    @State(defaultValue = "2", inputCreator = DeviceFrameSlider::class) index: Int,
    @State(defaultValue = "This is a long text that should be wrapped and not cut out") text: String,
) {
    Column {
        Text("AAA")

        DeviceFrame(
            Devices.all[index],
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
                    .background(Color.Red)
            )
            Text(text = text, fontSize = 30.sp)
        }
    }
}
