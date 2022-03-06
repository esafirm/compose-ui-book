import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import nolambda.uibook.sample.App

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}