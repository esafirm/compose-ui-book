package nolambda.uibook.factory

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import nolambda.uibook.annotations.BookMetaData
import nolambda.uibook.annotations.FunctionParameter
import nolambda.uibook.browser.BookHost
import nolambda.uibook.browser.form.ComposeEmitter
import nolambda.uibook.browser.form.FormCreator

public class TestFactory(
    private val child: @Composable BookHost.(String, String) -> Unit
) : BookFactory {
    private val function: String = """
      |{
      |    Row(verticalAlignment = Alignment.CenterVertically) {
      |        Image(
      |            painter = rememberImagePainter(imageUrl),
      |            contentDescription = "avatar",
      |            contentScale = ContentScale.Crop,
      |            modifier = Modifier
      |                .size(64.dp)
      |                .clip(CircleShape)
      |                .border(2.dp, androidx.compose.ui.graphics.Color.Black, CircleShape)
      |        )
      |        Spacer(modifier = Modifier.width(16.dp))
      |        Text(
      |            text = title,
      |            fontSize = 22.sp
      |        )
      |    }
      |}
      """.trimMargin()

    private val parameters: List<FunctionParameter> by lazy(LazyThreadSafetyMode.NONE) {
        listOf(
            FunctionParameter("imageUrl", "String", "https://dimsumaskitea.com/logo.png"),
            FunctionParameter("title", "String", "Dimsum Askitea"),
        )
    }


    private val meta: BookMetaData by lazy(LazyThreadSafetyMode.NONE) {
        BookMetaData(
            "Circular Image Sample", function, "kotlin", "CircularImage",
            "nolambda.uibook.samples", parameters, true
        )
    }


    public override fun getBook(config: BookConfig): ComposeEmitter {
        val state0 = mutableStateOf("https://dimsumaskitea.com/logo.png")
        val state1 = mutableStateOf("Dimsum Askitea")
        // Initiate form creator
        return FormCreator(config, meta,
            onUpdateState = {
                // Assign input to state
                state0.value = it[0] as kotlin.String
                state1.value = it[1] as kotlin.String
            },
            onChildCreation = {
                child(state0.value, state1.value)
            }
        ).create()
    }

    public override fun getMetaData(): BookMetaData = meta
}
