package nolambda.uibook.processors.generator

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.symbol.KSFile
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asTypeName
import com.squareup.kotlinpoet.buildCodeBlock
import com.squareup.kotlinpoet.ksp.KotlinPoetKspPreview
import com.squareup.kotlinpoet.ksp.writeTo
import nolambda.uibook.annotations.BookMetaData
import nolambda.uibook.annotations.FunctionParameter
import nolambda.uibook.annotations.UIBook
import nolambda.uibook.annotations.UIBookCons
import nolambda.uibook.processors.getTypeMirror
import nolambda.uibook.processors.utils.DefaultValueResolver
import nolambda.uibook.processors.utils.Logger
import java.util.*

@KotlinPoetKspPreview
class UIBookGenerator(
    private val codeGenerator: CodeGenerator,
    private val metaDatas: List<BookCreatorMetaData>,
    private val logger: Logger
) {

    private val bookConfigClass = ClassName("nolambda.uibook.factory", "BookConfig")
    private val viewClass = ClassName("android.view", "View")
    private val libraryClass = ClassName(UIBookCons.DEST_PACKAGE, UIBookCons.LIBRARY_CLASS_NAME)
    private val factoryInterface = ClassName(UIBookCons.DEST_PACKAGE, UIBookCons.FACTORY_INTERFACE_NAME)
    private val composeViewClass = ClassName("androidx.compose.ui.platform", "ComposeView")

    fun generate() {
        val bookClassNames = metaDatas.map { book -> createBookFactory(book) }
        val allFiles = metaDatas.map { it.originatingFile }.distinct()
        createLibrary(bookClassNames, allFiles)

        logger.note("Generated all books!")
    }

    private fun createLibrary(books: List<ClassName>, allFiles: List<KSFile>) {
        val list = ClassName("kotlin.collections", "List")
        val spec = FileSpec.builder(UIBookCons.DEST_PACKAGE, UIBookCons.LIBRARY_IMPL_CLASS_NAME)
            .addType(
                TypeSpec.classBuilder(UIBookCons.LIBRARY_IMPL_CLASS_NAME)
                    .addSuperinterface(libraryClass)
                    .addFunction(
                        FunSpec.builder("getBookFactories")
                            .addModifiers(KModifier.OVERRIDE)
                            .returns(list.parameterizedBy(factoryInterface))
                            .addCode(buildCodeBlock {
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

        spec.build().writeTo(codeGenerator, Dependencies(true, *allFiles.toTypedArray()))
    }

    private fun createBookFactory(creatorMetaData: BookCreatorMetaData): ClassName {
        val book = creatorMetaData.book
        val safeClassName = book.name.split(" ").joinToString("") {
            it.replaceFirstChar { char ->
                if (char.isLowerCase()) char.titlecase(Locale.getDefault()) else char.toString()
            }
        }
        val className = "${safeClassName}BookFactory"

        val functionProperty = PropertySpec.builder("function", String::class)
            .addModifiers(KModifier.PRIVATE)
            .initializer("%P", book.function)
            .build()

        val parametersProperty =
            PropertySpec.builder("parameters", List::class.parameterizedBy(FunctionParameter::class))
                .addModifiers(KModifier.PRIVATE)
                .delegate(buildCodeBlock {
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
            .delegate(buildCodeBlock {
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
            .addComposeImportIfNeeded(book)
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
                            .addCode(buildCodeBlock {

                                if (book.isComposeFunction) {
                                    addStatement("var composeView: %T? = null", composeViewClass)
                                    addComposeStateHolder(book)
                                }

                                addStatement("// Initiate form creator")
                                addFormCreatorConstructor(creatorMetaData)
                                indent()

                                if (book.isComposeFunction) {
                                    addComposeBookFunctionCall(book)
                                } else {
                                    addBookFunctionCall(book)
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

        file.writeTo(codeGenerator, Dependencies(aggregating = true, creatorMetaData.originatingFile))

        return ClassName(UIBookCons.DEST_PACKAGE, className)
    }

    @Suppress("DEPRECATION")
    private fun CodeBlock.Builder.addFormCreatorConstructor(book: BookCreatorMetaData) {
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

    private fun CodeBlock.Builder.addComposeStateHolder(book: BookMetaData) {
        book.parameters.forEachIndexed { index, param ->
            val value = DefaultValueResolver.createDefaultState(param)
            addStatement("val state${index} = mutableStateOf(${value})")
        }
    }

    /**
     * Add book function call to the code block
     *
     * Example:
     *
     * ## Catalogue
     * fun BookHost.SampleText() { … }
     *
     * ## Result in code block
     * SampleText()
     *
     * @param book - The book meta data
     */
    private fun CodeBlock.Builder.addBookFunctionCall(book: BookMetaData) {
        addFunctionCallWithParameter(book) { index, param ->
            addStatement("it[${index}] as %T,", TypeMapper.mapToClassName(param.type))
        }
    }

    /**
     * This is the same as [addBookFunctionCall] except it add ComposeView wrapper
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
    private fun CodeBlock.Builder.addComposeBookFunctionCall(book: BookMetaData) {
        addStatement("// Assign input to state")
        book.parameters.forEachIndexed { index, param ->
            addStatement("state${index}.value = it[${index}] as ${TypeMapper.mapToClassName(param.type)}")
        }

        addStatement("// Instantiate view for the first time")
        beginControlFlow("if (composeView == null)")
        addStatement("composeView = %T(context).apply {", composeViewClass)

        indent()
        addStatement("setContent {")

        addFunctionCallWithParameter(book) { index, _ ->
            addStatement("state${index}.value,")
        }

        // End of setContent
        addStatement("}")
        unindent()

        // End of apply
        addStatement("}")

        // End of if
        endControlFlow()

        addStatement("composeView!!")
    }

    /**
     * Base function for add function call with parameter
     *
     * Example:
     *
     * ## UI Book
     * fun BookHost.Text(param1: String, param2: String) { … }
     *
     * ## Result
     * Text(
     *  parameterStatement1,
     *  parameterStatement2,
     * )
     *
     * @param book - meta data
     * @param statementCreator - parameter statement creator
     */
    private fun CodeBlock.Builder.addFunctionCallWithParameter(
        book: BookMetaData,
        statementCreator: (Int, FunctionParameter) -> Unit
    ) {
        val hasParameter = book.parameters.isNotEmpty()
        if (hasParameter) {
            indent()
            addStatement("${book.functionName}(")

            indent()
            book.parameters.forEachIndexed { index, param ->
                statementCreator.invoke(index, param)
            }
            unindent()

            addStatement(")")
            unindent()
        } else {
            indent()
            addStatement("${book.functionName}()")
            unindent()
        }
    }

    private fun FileSpec.Builder.addComposeImportIfNeeded(book: BookMetaData): FileSpec.Builder {
        if (book.isComposeFunction) {
            addImport("androidx.compose.runtime", "mutableStateOf")
        }
        return this
    }
}
