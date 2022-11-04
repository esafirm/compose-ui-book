package nolambda.uibook.processors

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSType
import nolambda.uibook.annotations.BookMetaData
import nolambda.uibook.annotations.FunctionParameter
import nolambda.uibook.annotations.State
import nolambda.uibook.annotations.UIBook
import nolambda.uibook.annotations.code.CodeSpec
import nolambda.uibook.processors.generator.BookCreatorMetaData
import nolambda.uibook.processors.generator.CustomComponent
import nolambda.uibook.processors.utils.DefaultValueResolver
import nolambda.uibook.processors.utils.Logger
import org.jetbrains.kotlin.com.intellij.psi.PsiElement
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.KtFunction
import org.jetbrains.kotlin.psi.KtNamedFunction

@KspExperimental
class ProcessorHelper(
    private val el: KSFunctionDeclaration,
    private val psiElement: PsiElement,
    private val logger: Logger
) {

    private val psiParameters by lazy {
        (psiElement as? KtFunction)?.valueParameters?.map {
            val type = it.typeReference?.text.orEmpty()
            FunctionParameter(
                name = it.name.orEmpty(),
                type = type,
                defaultValue = DefaultValueResolver.getDefaultValueForType(type)
            )
        }.orEmpty()
    }

    private val elParameters by lazy {
        el.parameters.map { p ->
            val state: State? = p.getAnnotationsByType(State::class).firstOrNull()
            val type = p.type.toString()
            val defaultValue = state?.defaultValue ?: DefaultValueResolver.getDefaultValueForType(type)

            FunctionParameter(
                name = "",
                type = type,
                defaultValue = defaultValue
            )
        }
    }

    @KspExperimental
    fun createCreatorMetaData(ktFile: KtFile): BookCreatorMetaData? {
        if (isTheSame().not()) return null

        val codeSpec: CodeSpec? = el.getAnnotationsByType(CodeSpec::class).firstOrNull()
        val annotation = el.getAnnotationsByType(UIBook::class).first()

        val psiMethod = psiElement as KtNamedFunction

        val parameters = elParameters.zip(psiParameters) { elParam, psiParam ->
            FunctionParameter(
                name = psiParam.name,
                type = elParam.type,
                defaultValue = elParam.defaultValue
            )
        }

        return BookCreatorMetaData(
            annotation = annotation,
            customComponent = getCustomComponent(),
            book = BookMetaData(
                name = annotation.name,
                function = getFunctionCode(codeSpec, psiMethod),
                language = codeSpec?.language ?: "kotlin",
                functionName = el.simpleName.asString(),
                packageName = ktFile.packageName,
                parameters = parameters,
                isComposeFunction = psiMethod.isComposableAnnotationExists()
            ),
            originatingFile = el.containingFile!!
        )
    }

    private fun getCustomComponent(): CustomComponent {
        val ksAnnotation = el.annotations.firstOrNull {
            it.shortName.asString() == UIBook::class.simpleName
        }

        return CustomComponent(
            inputCreator = ksAnnotation.argumentAsKtype(1),
            viewStateProvider = ksAnnotation.argumentAsKtype(2),
        )
    }

    private fun KSAnnotation?.argumentAsKtype(index: Int): KSType? {
        if (this == null) return null
        return arguments.getOrNull(index)?.value as? KSType
    }

    private fun getFunctionCode(codeSpec: CodeSpec?, ktFunction: KtNamedFunction): String {
        val function = if (codeSpec?.code?.isNotBlank() == true) {
            codeSpec.code
        } else {
            ktFunction.bodyExpression!!.text
        }
        if (codeSpec != null) {
            if (codeSpec.trimIndent && codeSpec.trimMargin) {
                error("Only one of trimIndent or trimMargin can be true at the same time")
            }
            if (codeSpec.trimIndent) {
                return function.trimIndent()
            }
            if (codeSpec.trimMargin) {
                return function.trimMargin()
            }
        }
        return function
    }

    private fun isTheSame(): Boolean {
        if (psiElement !is KtFunction) return false
        if (el.simpleName.asString() != psiElement.name) return false
        if (el.parameters.size != psiElement.valueParameters.size) return false

        val isParameterTheSame = psiParameters.mapIndexed { index, t ->
            // TODO: more accurate way to check type
            elParameters[index].type.contains(t.type, ignoreCase = true)
        }.all { it }

        if (!isParameterTheSame) {
            logger.error("There's a parameter mistmatch that might be false positive: ${el.simpleName.asString()}")
        }

        return isParameterTheSame
    }

    private fun KtNamedFunction.isComposableAnnotationExists(): Boolean {
        return annotationEntries.any { it.shortName?.asString() == "Composable" }
    }
}
