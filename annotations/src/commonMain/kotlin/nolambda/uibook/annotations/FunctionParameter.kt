package nolambda.uibook.annotations

/**
 * Represent each parameter in UI book function
 *
 * For example
 *
 * ```kotlin
 * @UIBook
 * fun BookHost.Text(@Choices(values = arrayOf("a","b")) aText: String = "Some text") {...}
 * ```
 *
 * In above example function parameter will represent `aText` with value
 *
 * name: aText
 * type: String
 * defaultValue: "Some text"
 * annotation: Choices(values = arrayOf("a","b"))
 *
 */
data class FunctionParameter(
    val name: String,
    val type: String,
    val defaultValue: String
)
