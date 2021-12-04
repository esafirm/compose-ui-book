package nolambda.uibook.processors

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.squareup.kotlinpoet.ksp.KotlinPoetKspPreview
import nolambda.uibook.annotations.UIBook
import nolambda.uibook.processors.generator.BookCreatorMetaData
import nolambda.uibook.processors.generator.UIBookGenerator
import nolambda.uibook.processors.utils.Logger
import nolambda.uibook.processors.utils.SourceCodeLocator

class UIBookSymbolProcessor(
    private val processingEnv: SymbolProcessorEnvironment
) : SymbolProcessor {

    private val logger by lazy { Logger(processingEnv) }
    private val sourceCodeLocator by lazy { SourceCodeLocator(logger) }

    @KspExperimental
    @KotlinPoetKspPreview
    override fun process(resolver: Resolver): List<KSAnnotated> {
        val metas = mutableListOf<BookCreatorMetaData>()
        val annotationName = UIBook::class.qualifiedName ?: error("Annotation name cannot be resolved")

        resolver.getSymbolsWithAnnotation(annotationName).forEach { el ->
            if (el !is KSFunctionDeclaration) {
                logger.error("Annotation can only be applied to method")
                return emptyList()
            }

            val ktFile = sourceCodeLocator.sourceOf(el).createKtFile()
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
        return emptyList()
    }

    @KotlinPoetKspPreview
    private fun generateFiles(metas: List<BookCreatorMetaData>) {
        logger.note("Generating ${metas.size} files!")

        UIBookGenerator(processingEnv.codeGenerator, metas, logger).generate()
    }
}