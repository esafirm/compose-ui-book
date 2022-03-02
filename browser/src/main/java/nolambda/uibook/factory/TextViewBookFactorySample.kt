package nolambda.uibook.factory

import android.view.View
import android.widget.TextView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import nolambda.uibook.annotations.BookMetaData
import nolambda.uibook.annotations.FunctionParameter
import nolambda.uibook.browser.BookHost
import nolambda.uibook.browser.form.ComposeEmitter
import nolambda.uibook.browser.form.ComposeViewCreator
import nolambda.uibook.browser.form.FormCreator
import nolambda.uibook.components.bookform.AndroidContainer

public class TextViewBookFactorySample : BookFactory {
  private val function: String = """
      |{
      |    /**
      |     * This will draw text
      |     */
      |    return TextView(context).apply {
      |        this.text = text
      |        setTextColor(Color.RED)
      |    }
      |}
      """.trimMargin()

  private val parameters: List<FunctionParameter> by lazy(LazyThreadSafetyMode.NONE) {
    listOf(
      FunctionParameter("text", "String", "This is a text"),
    )
  }


  private val meta: BookMetaData by lazy(LazyThreadSafetyMode.NONE) {
    BookMetaData("TextView", function, "kotlin", "createTextView", "nolambda.uibook.samples",
        parameters, false)
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
      AndroidContainer(createTextView(
        state0.value,
      ))
    }
    return FormCreator(config, meta, onUpdateState, onChildCreation).create()
  }

  public override fun getMetaData(): BookMetaData = meta
}

fun BookHost.createTextView(text: String) : View {
  return TextView(context).apply {
    this.text = text
  }
}
