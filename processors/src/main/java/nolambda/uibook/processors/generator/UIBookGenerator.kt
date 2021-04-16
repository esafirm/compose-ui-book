package nolambda.uibook.processors.generator

import com.squareup.kotlinpoet.*
import nolambda.uibook.annotations.BookMetaData
import nolambda.uibook.annotations.FunctionParameter
import java.io.File

class UIBookGenerator(
    private val destDirectory: String,
    private val books: List<BookMetaData>
) {

    companion object {
        private const val DEST_PACKAGE = "nolambda.uibook.factory"
    }

    fun generate() {
        val destDir = File(destDirectory)
        if (!destDir.exists()) destDir.mkdirs()

        books.forEach { book ->
            createClassWithPoet(destDir, book)
        }
    }

    private fun createClassWithPoet(
        dest: File,
        book: BookMetaData
    ) {
        val className = "${book.name}BookFactory"

        val contextClass = ClassName("android.content", "Context")
        val viewClass = ClassName("android.view", "View")
        val formCreatorClass = ClassName("nolambda.uibook.browser", "FormCreator")

        val file = FileSpec.builder(DEST_PACKAGE, className)
            .addImport(book.packageName, book.functionName)
            .addType(
                TypeSpec.classBuilder(className)
                    .addFunction(
                        FunSpec.builder("getView")
                            .addParameter("context", contextClass)
                            .addStatement("val f = %P", book.function)
                            .addCode(buildCodeBlock {
                                addStatement("val parameters = listOf(")
                                indent()
                                book.parameters.forEach {
                                    addStatement("%T(%S, %S),", FunctionParameter::class, it.name, it.type)
                                }
                                unindent()
                                addStatement(")")
                            })
                            .addStatement(
                                "val meta = %T(%S, f, %S, %S, parameters)",
                                BookMetaData::class,
                                book.name,
                                book.functionName,
                                book.packageName
                            )
                            .addStatement(
                                "return %T(context, meta).create { context.${book.functionName}(it) }",
                                formCreatorClass,
                            )
                            .returns(viewClass)
                            .build()
                    ).build()
            ).build()

        file.writeTo(dest)
    }
}
