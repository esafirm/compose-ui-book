package nolambda.uibook.processors

import com.tschuchort.compiletesting.KotlinCompilation
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class UIBookProcessorTest : AbstractKspTest(UIBookProcessorProvider()) {

    @Test
    fun `test simple`() {
        val result = compile(
            """
            package test
            
            import nolambda.uibook.annotations.UIBook
            import androidx.compose.runtime.Composable
            import androidx.compose.material.Text
            
            
            @UIBook(name = "Compose code")
            @Composable
            fun BookHost.SampleText(text: String) {
                Text(text = text)
            } 
        """.trimIndent()
        )

        assertEquals(KotlinCompilation.ExitCode.COMPILATION_ERROR, result.exitCode)
        assertTrue(result.messages.contains("The entity class must have at least one id property."))
    }

}
