package nolambda.uibook.sample.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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

@UIBook(name = "Compose code")
@Composable
fun BookHost.SampleText(text: String) {
    Text(text = text)
}

@UIBook(name = "Circular Image Sample")
@Composable
fun BookHost.CircularImage(
    @State(defaultValue = "https://silly-kowalevski-bc3c3d.netlify.app/logo.png") imageUrl: String,
    @State(defaultValue = "Dimsum Askitea") title: String
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
    @State(defaultValue = "200") childWidth: Int
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
