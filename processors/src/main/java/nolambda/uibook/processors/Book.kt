package nolambda.uibook.processors

import nolambda.uibook.annotations.BookMetaData

data class Book(
    val meta: BookMetaData,
    val sourceCode: SourceCodeLocator.SourceCodeResult
)