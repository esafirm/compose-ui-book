package nolambda.uibook.processors

import com.google.auto.service.AutoService
import com.sun.source.util.Trees
import nolambda.uibook.annotations.BookMetaData
import nolambda.uibook.annotations.UIBook
import org.jetbrains.kotlin.cli.jvm.compiler.EnvironmentConfigFiles
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment
import org.jetbrains.kotlin.com.intellij.openapi.util.Disposer
import org.jetbrains.kotlin.com.intellij.psi.PsiManager
import org.jetbrains.kotlin.com.intellij.testFramework.LightVirtualFile
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.idea.KotlinFileType
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.KtFunction
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.ElementKind
import javax.lang.model.element.TypeElement

@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor::class)
class UIBookProcessor : AbstractProcessor() {

    companion object {
        private const val OPTION_KAPT_KOTLIN_GENERATED = "kapt.kotlin.generated"
    }

    private val logger by lazy { Logger(processingEnv) }
    private val sourceCodeLocator by lazy { SourceCodeLocator(logger) }

    private val project by lazy {
        KotlinCoreEnvironment.createForProduction(
            Disposer.newDisposable(),
            CompilerConfiguration(),
            EnvironmentConfigFiles.JVM_CONFIG_FILES
        ).project
    }

    private lateinit var trees: Trees

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf(
            UIBook::class.java.name,
        )
    }

    private fun createKtFile(codeString: String, fileName: String) =
        PsiManager.getInstance(project)
            .findFile(
                LightVirtualFile(fileName, KotlinFileType.INSTANCE, codeString)
            ) as KtFile

    override fun init(processingEnvironment: ProcessingEnvironment?) {
        super.init(processingEnvironment)
        trees = Trees.instance(processingEnvironment)
    }

    override fun process(
        p0: MutableSet<out TypeElement>?,
        env: RoundEnvironment
    ): Boolean {

        val books = mutableListOf<Book>()

        env.getElementsAnnotatedWith(UIBook::class.java).forEach { el ->
            if (el.kind != ElementKind.METHOD) {
                logger.error("Annotation can only be applied to method")
                return false
            }

            val annotation = el.getAnnotation(UIBook::class.java)
            val functionName = el.simpleName.toString()
            val result = sourceCodeLocator.sourceOf(el, processingEnv)
            val ktFile = createKtFile(result.sourceCode, result.fileName)

            ktFile.children.forEach {
                if (it is KtFunction) {
                    if (it.name == functionName) {
                        val function = it.bodyExpression!!.text
                        books.add(
                            Book(
                                meta = BookMetaData(
                                    name = annotation.name,
                                    function = function,
                                    functionName = functionName,
                                    packageName = ktFile.packageName ?: ""
                                ),
                                sourceCode = result,
                            )
                        )
                    }
                }
            }
        }

        if (books.isNotEmpty()) {
            generateFiles(books)
        }

        return true
    }

    private fun generateFiles(books: List<Book>) {
        logger.note("Generating ${books.size} files!")

        val dest = processingEnv.options[OPTION_KAPT_KOTLIN_GENERATED]
            ?: throw IllegalStateException("Kapt option not exist")
        UIBookGenerator(dest, books).generate()
    }

}