package nolambda.uibook.annotations

data class BookMetaData(
    val name: String,
    val function: String,
    val language: String,
    val functionName: String,
    val packageName: String,
    val parameters: List<FunctionParameter>,
    val isComposeFunction: Boolean
)
