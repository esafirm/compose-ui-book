package nolambda.uibook.processors

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import nolambda.uibook.annotations.BookMetaData
import nolambda.uibook.annotations.FunctionParameter
import nolambda.uibook.annotations.State
import nolambda.uibook.annotations.UIBook
import nolambda.uibook.annotations.code.CodeSpec
import nolambda.uibook.processors.generator.BookCreatorMetaData
import nolambda.uibook.processors.utils.DefaultValueResolver
import nolambda.uibook.processors.utils.Logger
import org.jetbrains.kotlin.com.intellij.psi.PsiElement
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.KtFunction
import org.jetbrains.kotlin.psi.KtNamedFunction
import javax.lang.model.element.ExecutableElement

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
        (el as? ExecutableElement)?.parameters?.mapIndexed { index, p ->
            if (index == 0) {
                null
            } else {
                val state: State? = p.getAnnotation(State::class.java)
                val type = p.asType().toString()
                val defaultValue = state?.defaultValue ?: DefaultValueResolver.getDefaultValueForType(type)

                FunctionParameter(
                    name = "",
                    type = type,
                    defaultValue = defaultValue
                )
            }
        }?.filterNotNull().orEmpty()
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
            book = BookMetaData(
                name = annotation.name,
                function = getFunctionCode(codeSpec, psiMethod),
                language = codeSpec?.language ?: "kotlin",
                functionName = el.simpleName.toString(),
                packageName = ktFile.packageName,
                parameters = parameters,
                isComposeFunction = psiMethod.isComposableAnnotationExists()
            ),
            originatingFile = el.containingFile!!
        )
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
        if (el !is ExecutableElement) return false
        if (el.simpleName.toString() != psiElement.name) return false
        if ((el.parameters.size - 1) != psiElement.valueParameters.size) return false

        val isParameterTheSame = psiParameters.mapIndexed { index, t ->
            // TODO: more accurate way to check type
            elParameters[index].type.contains(t.type, ignoreCase = true)
        }.all { it }

        if (!isParameterTheSame) {
            logger.error("There's a parameter mistmatch that might be false positive: ${el.simpleName}")
        }

        return isParameterTheSame
    }

    private fun KtNamedFunction.isComposableAnnotationExists(): Boolean {
        return annotationEntries.any { it.shortName?.asString() == "Composable" }
    }
}
