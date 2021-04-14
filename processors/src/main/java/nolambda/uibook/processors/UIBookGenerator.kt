package nolambda.uibook.processors

import java.io.File

class UIBookGenerator(
    private val destDirectory: String,
    private val books: List<Book>
) {

    companion object {
        private const val FILE_SUFFIX = "BookFactory.kt"
        private const val PACKAGE = "nolambda/uibook/factory"
    }

    private val MULTI_STRING = "\"\"\"".replace("\\", "")

    fun generate() {
        val packageName = PACKAGE.replace("/", ".")
        val realDest = File("$destDirectory/$PACKAGE")

        if (!realDest.exists()) realDest.mkdirs()

        books.forEach { book ->
            val name = "${book.meta.name}$FILE_SUFFIX"
            val file = File(realDest, name)
            file.writeText(createClass(packageName, book))
        }
    }

    private fun escapeCode(code: String, multi: Boolean = false) = if(multi) {
        "$MULTI_STRING$code$MULTI_STRING"
    }else{
        "\"$code\""
    }

    private fun createClass(
        packageName: String,
        book: Book
    ): String {

        val meta = book.meta

        return """
        |package $packageName
        
        |import android.content.Context
        |import android.view.View
        |import nolambda.uibook.browser.FormCreator
        |import nolambda.uibook.annotations.BookMetaData
        |import ${meta.packageName}.${meta.functionName}
        
        |class ${meta.name}BookFactory {
        |    fun getView(context: Context): View {
        |       val function = ${escapeCode(book.meta.function, multi = true)}
        |       val meta = BookMetaData(${escapeCode(meta.name)}, function, ${escapeCode(meta.functionName)}, ${escapeCode(meta.packageName)})
        |       return FormCreator(context, meta).create {
        |           context.createTextView(it)
        |       }
        |    }
        |    
        |}    
        """.trimMargin()
    }
}
