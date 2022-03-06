package nolambda.uibook.sample.component

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jetbrains.skia.Image

object ImageLoader {

    private val httpClient by lazy { OkHttpClient() }

    @Suppress("BlockingMethodInNonBlockingContext")
    suspend fun load(url: String): ImageBitmap? {
        println("Load image: $url")

        return withContext(Dispatchers.IO) {
            val call = httpClient.newCall(
                Request.Builder()
                    .url(url)
                    .build()
            )

            val response = call.execute()
            val bytes = response.body?.bytes()
            if (bytes == null) {
                println("Bytes is empty")
                return@withContext null
            }
            Image.makeFromEncoded(bytes).toComposeImageBitmap()
        }
    }
}