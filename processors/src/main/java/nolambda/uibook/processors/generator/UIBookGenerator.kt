package nolambda.uibook.processors.generator

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import nolambda.uibook.annotations.BookMetaData
import nolambda.uibook.annotations.FunctionParameter
import nolambda.uibook.annotations.UIBook
import nolambda.uibook.annotations.UIBookCons
import nolambda.uibook.processors.getTypeMirror
import java.io.File
import java.util.*
import com.squareup.kotlinpoet.buildCodeBlock as buildCodeBlock1

class UIBookGenerator(
    private val destDirectory: String,
    private val metaDatas: List<BookCreatorMetaData>
) {

    private val destDir by lazy {
        val destDir = File(destDirectory)
        if (!destDir.exists()) destDir.mkdirs()
        destDir
    }

    private val bookConfigClass = ClassName("nolambda.uibook.factory", "BookConfig")
    private val viewClass = ClassName("android.view", "View")
    private val libraryClass = ClassName(UIBookCons.DEST_PACKAGE, UIBookCons.LIBRARY_CLASS_NAME)
    private val factoryInterface = ClassName(UIBookCons.DEST_PACKAGE, UIBookCons.FACTORY_INTERFACE_NAME)
    private val composeViewClass = ClassName("androidx.compose.ui.platform", "ComposeView")

    fun generate() {
        val bookClassNames = metaDatas.map { book -> createBookFactory(book) }
        createLibrary(bookClassNames)
    }

    private fun createLibrary(books: List<ClassName>) {
        val list = ClassName("kotlin.collections", "List")
        val spec = FileSpec.builder(UIBookCons.DEST_PACKAGE, UIBookCons.LIBRARY_IMPL_CLASS_NAME)
            .addType(
                TypeSpec.classBuilder(UIBookCons.LIBRARY_IMPL_CLASS_NAME)
                    .addSuperinterface(libraryClass)
                    .addFunction(
                        FunSpec.builder("getBookFactories")
                            .addModifiers(KModifier.OVERRIDE)
                            .returns(list.parameterizedBy(factoryInterface))
                            .addCode(buildCodeBlock1 {
                                addStatement("// List all book factories in here")
                                addStatement("return listOf(")
                                indent()
                                books.forEach { book ->
                                    addStatement("%T(),", book)
                                }
                                unindent()
                                addStatement(")")
                            })
                            .build()
                    ).build()
            )

        spec.build().writeTo(destDir)
    }

    private fun createBookFactory(creatorMetaData: BookCreatorMetaData): ClassName {
        val book = creatorMetaData.book
        val safeClassName = book.name.split(" ").joinToString("") { it.capitalize(Locale.getDefault()) }
        val className = "${safeClassName}BookFactory"

        val functionProperty = PropertySpec.builder("function", String::class)
            .addModifiers(KModifier.PRIVATE)
            .initializer("%P", book.function)
            .build()

        val parametersProperty =
            PropertySpec.builder("parameters", List::class.parameterizedBy(FunctionParameter::class))
                .addModifiers(KModifier.PRIVATE)
                .delegate(buildCodeBlock1 {
                    beginControlFlow("lazy(%T.NONE)", LazyThreadSafetyMode::class)
                    addStatement("listOf(")
                    indent()
                    book.parameters.forEach {
                        addStatement("%T(%S, %S, %S),", FunctionParameter::class, it.name, it.type, it.defaultValue)
                    }
                    unindent()
                    addStatement(")")
                    endControlFlow()
                })
                .build()

        val metaDataProperty = PropertySpec.builder("meta", BookMetaData::class)
            .addModifiers(KModifier.PRIVATE)
            .delegate(buildCodeBlock1 {
                beginControlFlow("lazy(%T.NONE)", LazyThreadSafetyMode::class)
                addStatement(
                    "%T(%S, function, %S, %S, %S, parameters, %L)",
                    BookMetaData::class,
                    book.name,
                    book.language,
                    book.functionName,
                    book.packageName,
                    book.isComposeFunction
                )
                endControlFlow()
            })
            .build()

        val file = FileSpec.builder(UIBookCons.DEST_PACKAGE, className)
            .addImport(book.packageName, book.functionName)
            .addType(
                TypeSpec.classBuilder(className)
                    .addSuperinterface(factoryInterface)
                    .addProperty(functionProperty)
                    .addProperty(parametersProperty)
                    .addProperty(metaDataProperty)
                    .addFunction(
                        FunSpec.builder("getBook")
                            .addModifiers(KModifier.OVERRIDE)
                            .addParameter("config", bookConfigClass)
                            .addCode(buildCodeBlock1 {
                                addStatement("// Initiate form creator")
                                createFormCreatorConstructor(creatorMetaData)
                                indent()

                                if (book.isComposeFunction) {
                                    createComposeBookFunctionCall(book)
                                } else {
                                    createBookFunctionCall(book)
                                }

                                unindent()
                                addStatement("}")
                            })
                            .returns(viewClass)
                            .build()
                    )
                    .addFunction(
                        FunSpec.builder("getMetaData")
                            .addModifiers(KModifier.OVERRIDE)
                            .addStatement("return meta")
                            .returns(BookMetaData::class)
                            .build()
                    )
                    .build()
            ).build()

        file.writeTo(destDir)

        return ClassName(UIBookCons.DEST_PACKAGE, className)
    }

    @Suppress("DEPRECATION")
    private fun CodeBlock.Builder.createFormCreatorConstructor(book: BookCreatorMetaData) {
        val formCreatorClass = ClassName("nolambda.uibook.browser.form", "FormCreator")
        val inputCreatorType = getTypeMirror { book.annotation.inputCreator }
        val stateProviderType = getTypeMirror { book.annotation.viewStateProvider }

        val defaultTypeName = UIBook.Default::class.asTypeName()
        val isDefaultInputCreator = inputCreatorType.asTypeName() == defaultTypeName
        val isDefaultViewStateProvider = stateProviderType.asTypeName() == defaultTypeName

        var statementInput = ""
        if (!isDefaultInputCreator) {
            addStatement("val inputCreator = %T()", inputCreatorType)
            statementInput = ", inputCreator = inputCreator"
        }

        var statementViewState = ""
        if (!isDefaultViewStateProvider) {
            addStatement("val stateProvider = %T()", stateProviderType)
            statementViewState = ", stateProvider = stateProvider"
        }

        addStatement("return %T(config, meta$statementInput$statementViewState).create {", formCreatorClass)
    }

    /**
     * Add book function call to the code block
     *
     * Example:
     *
     * ## Catalogue
     * fun BookHost.SampleText() {
     * }
     *
     * ## Result in code block
     * SampleText()
     *
     * @param book - The book meta data
     */
    private fun CodeBlock.Builder.createBookFunctionCall(book: BookMetaData) {
        val hasParameter = book.parameters.isNotEmpty()
        if (hasParameter) {
            indent()
            addStatement("${book.functionName}(")
            book.parameters.forEachIndexed { index, param ->
                addStatement("it[${index}] as %T,", TypeMapper.mapToClassName(param.type))
            }
            addStatement(")")
            unindent()
        } else {
            indent()
            addStatement("${book.functionName}()")
            unindent()
        }
    }

    /**
     * This is the same as [createBookFunctionCall] except it add ComposeView wrapper
     * around the function call
     *
     * Example:
     *
     * ## Catalogue
     * @Composable
     * fun BookHost.SampleText() {}
     *
     * ## Result in code block
     * ComposeView(context) {
     *   setContent {
     *     SampleText()
     *   }
     * }
     *
     * @param book - The book meta data
     */
    private fun CodeBlock.Builder.createComposeBookFunctionCall(book: BookMetaData) {
        addStatement("%T(context).apply {", composeViewClass)
        indent()
        addStatement("setContent {")
        createBookFunctionCall(book)
        addStatement("}")
        unindent()
        addStatement("}")
    }
}
