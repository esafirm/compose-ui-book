package nolambda.uibook.annotations

import java.io.Serializable

data class BookMetaData(
    val name: String,
    val function: String,
    val functionName: String,
    val packageName: String
) : Serializable