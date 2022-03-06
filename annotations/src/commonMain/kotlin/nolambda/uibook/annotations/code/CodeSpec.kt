package nolambda.uibook.annotations.code

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class CodeSpec(
    /**
     * Code that will show in source code tab
     */
    val code: String = "",

    /**
     * Will execute [String.trimIndent] to [code]
     * If [code] is empty or blank this will be ignored
     */
    val trimIndent: Boolean = false,

    /**
     * Will execute [String.trimMargin] with default margin "|" to [code]
     * If [code] is blank, this will be ignored
     */
    val trimMargin: Boolean = false,

    /**
     * Programming language the [code] is written
     * This is used for syntax highlighting
     *
     * List of available languages:
     * https://github.com/kbiakov/CodeView-Android#list-of-available-languages--their-extensions
     */
    val language: String = "kotlin"
)
