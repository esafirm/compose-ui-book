package nolambda.uibook.processors.generator

import com.squareup.kotlinpoet.FileSpec
import nolambda.uibook.annotations.BookMetaData

internal fun FileSpec.Builder.addComposeImportIfNeeded(book: BookMetaData): FileSpec.Builder {
    if (book.isComposeFunction) {
        addImport("androidx.compose.runtime", "mutableStateOf")
    }
    return this
}