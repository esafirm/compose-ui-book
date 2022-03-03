package nolambda.uibook.factory

import androidx.compose.material.Text
import androidx.compose.runtime.mutableStateOf
import kotlin.LazyThreadSafetyMode
import kotlin.String
import kotlin.collections.List
import nolambda.uibook.annotations.BookMetaData
import nolambda.uibook.annotations.FunctionParameter
import nolambda.uibook.browser.form.ComposeEmitter
import nolambda.uibook.browser.form.ComposeViewCreator
import nolambda.uibook.browser.form.FormCreator

public class ComposeCodeBookFactory : BookFactory {
  private val function: String = """
      |{
      |    Text(text = text)
      |}
      """.trimMargin()

  private val parameters: List<FunctionParameter> by lazy(LazyThreadSafetyMode.NONE) {
    listOf(
      FunctionParameter("text", "String", "This is a text"),
    )
  }


  private val meta: BookMetaData by lazy(LazyThreadSafetyMode.NONE) {
    BookMetaData("Compose code", function, "kotlin", "SampleText", "nolambda.uibook.samples",
        parameters, true)
  }


  public override fun getBook(config: BookConfig): ComposeEmitter {
    val state0 = mutableStateOf("This is a text")
    // Initiate form creator
    val onUpdateState = { it: Array<Any> ->
      // Assign input to state
      state0.value = it[0] as kotlin.String
    }
    val onChildCreation: ComposeViewCreator = {
      // Instantiate view for the first time
      Text("A")
    }
    return FormCreator(config, meta, onUpdateState, onChildCreation).create()
  }

  public override fun getMetaData(): BookMetaData = meta
}
