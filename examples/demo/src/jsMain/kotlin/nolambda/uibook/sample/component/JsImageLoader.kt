package nolambda.uibook.sample.component

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import kotlinx.browser.window
import kotlinx.coroutines.await
import org.jetbrains.skia.Image
import org.khronos.webgl.ArrayBuffer
import org.khronos.webgl.Int8Array
import org.w3c.fetch.NO_CORS
import org.w3c.fetch.RequestInit
import org.w3c.fetch.RequestMode

object JsImageLoader {
    suspend fun load(url: String): ImageBitmap? {
        println("Load image: $url")

        val res = window.fetch(url).await()
        return if (res.ok) {
            val bytes = res.arrayBuffer().await().toByteArray()
            Image.makeFromEncoded(bytes).toComposeImageBitmap()
        } else {
            println("Error: ${res.status} - ${res.statusText}")
            null
        }
    }

    private fun ArrayBuffer.toByteArray(): ByteArray = Int8Array(this).unsafeCast<ByteArray>()
}
