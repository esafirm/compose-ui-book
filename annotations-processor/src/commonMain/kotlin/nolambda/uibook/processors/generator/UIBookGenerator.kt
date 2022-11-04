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
import com.squareup.kotlinpoet.ksp.toTypeName
import com.squareup.kotlinpoet.ksp.writeTo
import com.squareup.kotlinpoet.withIndent
import nolambda.uibook.annotations.BookMetaData
import nolambda.uibook.annotations.FunctionParameter
import nolambda.uibook.annotations.UIBook
import nolambda.uibook.annotations.UIBookCons
import nolambda.uibook.processors.utils.DefaultValueResolver
import nolambda.uibook.processors.utils.Logger
import java.util.Locale

@KotlinPoetKspPreview
class UIBookGenerator(
    private val codeGenerator: CodeGenerator,
    private val metaDatas: List<BookCreatorMetaData>,
    private val logger: Logger
) {

    private val bookConfigClass = ClassName("nolambda.uibook.factory", "BookConfig")
    private val libraryClass = ClassName(UIBookCons.DEST_PACKAGE, UIBookCons.LIBRARY_CLASS_NAME)
    private val factoryInterface = ClassName(UIBookCons.DEST_PACKAGE, UIBookCons.FACTORY_INTERFACE_NAME)
    private val composeEmitterClass = ClassName("nolambda.uibook.browser.form", "ComposeEmitter")
    private val composeViewCreatorClass = ClassName("nolambda.uibook.browser.form", "ComposeViewCreator")

    // Android Specific
    private val androidContainerClass = ClassName("nolambda.uibook.components.bookform", "AndroidContainer")
    private val androidBookHost = ClassName("nolambda.uibook.browser", "AndroidBookHost")

    // Functions
    private val mutableStateOfClass = ClassName("androidx.compose.runtime", "mutableStateOf")

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

                                addComposeStateHolder(book)
                                addStatement("// Initiate form creator")
                                addFormCreatorConstructor(creatorMetaData)

                            })
                            .returns(composeEmitterClass)
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

    /**
     * Create FormCreator block that eventually will be called by the browser
     * Form creator handles the input from browser and update accordingly to the view itself
     *
     * Example:
     *
     * ```kotlin
     * FormCreator(config, meta, onUpdateState, onChildCreation).create()
     * ```
     */
    @Suppress("DEPRECATION")
    private fun CodeBlock.Builder.addFormCreatorConstructor(bookCreator: BookCreatorMetaData) {
        val formCreatorClass = ClassName("nolambda.uibook.browser.form", "FormCreator")
        val inputCreatorType = bookCreator.customComponent.inputCreator?.toTypeName()
        val stateProviderType = bookCreator.customComponent.viewStateProvider?.toTypeName()

        val defaultTypeName = UIBook.Default::class.asTypeName()
        val isDefaultInputCreator = inputCreatorType == null || inputCreatorType == defaultTypeName
        val isDefaultViewStateProvider = stateProviderType == null || stateProviderType == defaultTypeName

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

        // Add update state
        beginControlFlow("val onUpdateState = { it: Array<Any> ->")
        addStatement("// Assign input to state")
        bookCreator.book.parameters.forEachIndexed { index, param ->
            addStatement("state${index}.value = it[${index}] as ${TypeMapper.mapToClassName(param.type)}")
        }
        endControlFlow()

        addBookFunctionCall(bookCreator.book)

        addStatement(
            "return %T(config, meta, onUpdateState, onChildCreation$statementInput$statementViewState).create()",
            formCreatorClass
        )
    }

    /**
     * Add compose state holder
     *
     * Example:
     * ```kotlin
     * val state0 = mutableStateOf("This is text)
     * ```
     */
    private fun CodeBlock.Builder.addComposeStateHolder(book: BookMetaData) {
        book.parameters.forEachIndexed { index, param ->
            val value = DefaultValueResolver.createDefaultState(param)
            addStatement("val state${index} = %T(${value})", mutableStateOfClass)
        }
    }

    /**
     * Add book function call to the code block
     *
     * Example:
     *
     * ### Catalogue
     *
     * ```kotlin
     * @Composable
     * fun BookHost.SampleText() {}
     * ```
     *
     * ### Result in code block
     *
     * ```kotlin
     * val onChildrenCreation : ComposeViewCreator = {
     *   SampleText()
     * }
     * ```
     *
     * @param book - The book meta data
     */
    private fun CodeBlock.Builder.addBookFunctionCall(book: BookMetaData) {
        beginControlFlow("val onChildCreation: %T = {", composeViewCreatorClass)
        addStatement("// Instantiate view for the first time")

        // If android, then wrap it in AndroidContainer
        val isAndroidView = book.isComposeFunction.not()
        if (isAndroidView) {
            addStatement("this as %T", androidBookHost)
            addStatement("%T(", androidContainerClass)
            indent()
        }

        addFunctionCallWithParameter(book) { index, _ ->
            addStatement("state${index}.value,")
        }

        // End of wrap
        if (isAndroidView) {
            unindent()
            addStatement(")")
        }

        endControlFlow()
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
            addStatement("${book.functionName}(")

            withIndent {
                book.parameters.forEachIndexed { index, param ->
                    statementCreator.invoke(index, param)
                }
            }

            addStatement(")")
        } else {
            addStatement("${book.functionName}()")
        }
    }
}
