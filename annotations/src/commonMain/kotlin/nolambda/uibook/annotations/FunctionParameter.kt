package nolambda.uibook.annotations

/**
 * Represent each parameter in UI book function
 *
 * For example
 *
 * ```kotlin
 * @UIBook
 * fun BookHost.Text(@State(inputCreator = ButtonInput::class) aText: String = "Some text") {...}
 * ```
 *
 * In above example function parameter will represent `aText` with value
 *
 * name: aText
 * type: String
 * defaultValue: "Some text"
 *
 */
data class FunctionParameter(
    val name: String,
    val type: String,
    val defaultValue: String
) {
    val isNumberType get() = type == "Int" || type == "Float" || type == "Double" || type == "Long"
}
