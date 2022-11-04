package nolambda.uibook.processors.generator

import com.google.devtools.ksp.symbol.KSFile
import com.google.devtools.ksp.symbol.KSType
import com.squareup.kotlinpoet.TypeName
import nolambda.uibook.annotations.BookMetaData
import nolambda.uibook.annotations.UIBook
import kotlin.reflect.KType

data class BookCreatorMetaData(
    val annotation: UIBook,
    val customComponent: CustomComponent,
    val book: BookMetaData,
    val originatingFile: KSFile
)

/**
 * This class is used because we can't fetch the [KClass] in KSP
 * In java annotation processing, we still can use TypeMirrorException
 */
class CustomComponent(
    val inputCreator: KSType? = null,
    val viewStateProvider: KSType? = null
)