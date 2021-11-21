package nolambda.uibook.processors

import com.google.auto.service.AutoService
import com.sun.source.util.Trees
import nolambda.uibook.annotations.UIBook
import nolambda.uibook.annotations.code.CodeSpec
import nolambda.uibook.processors.generator.BookCreatorMetaData
import nolambda.uibook.processors.generator.UIBookGenerator
import nolambda.uibook.processors.utils.Logger
import nolambda.uibook.processors.utils.SourceCodeLocator
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

    private lateinit var trees: Trees

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf(
            UIBook::class.java.name,
        )
    }

    override fun init(processingEnvironment: ProcessingEnvironment?) {
        super.init(processingEnvironment)
        trees = Trees.instance(processingEnvironment)
    }

    override fun process(
        p0: MutableSet<out TypeElement>?,
        env: RoundEnvironment
    ): Boolean {
        val metas = mutableListOf<BookCreatorMetaData>()

        env.getElementsAnnotatedWith(UIBook::class.java).forEach { el ->
            if (el.kind != ElementKind.METHOD) {
                logger.error("Annotation can only be applied to method")
                return false
            }

            val ktFile = sourceCodeLocator.sourceOf(el, processingEnv).createKtFile()
            ktFile.children.forEach { psiElement ->
                val helper = ProcessorHelper(el, psiElement, logger)
                val creatorMetaData = helper.createCreatorMetaData(ktFile)
                if (creatorMetaData != null) {
                    metas.add(creatorMetaData)
                }
            }
        }

        if (metas.isNotEmpty()) {
            generateFiles(metas)
        }

        return true
    }

    private fun generateFiles(metas: List<BookCreatorMetaData>) {
        logger.note("Generating ${metas.size} files!")

        val dest = processingEnv.options[OPTION_KAPT_KOTLIN_GENERATED]
            ?: throw IllegalStateException("Kapt option not exist")
        UIBookGenerator(dest, metas, logger).generate()
    }

}