package nolambda.uibook.processors.generator

import com.google.devtools.ksp.symbol.KSFile
import nolambda.uibook.annotations.BookMetaData
import nolambda.uibook.annotations.UIBook

data class BookCreatorMetaData(
    val annotation: UIBook,
    val book: BookMetaData,
    val originatingFile: KSFile
)