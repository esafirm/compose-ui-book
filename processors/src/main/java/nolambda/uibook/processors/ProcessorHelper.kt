package nolambda.uibook.processors

import nolambda.uibook.annotations.BookMetaData
import nolambda.uibook.annotations.FunctionParameter
import nolambda.uibook.annotations.UIBook
import org.jetbrains.kotlin.com.intellij.psi.PsiElement
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.KtFunction
import org.jetbrains.kotlin.psi.KtNamedFunction
import javax.lang.model.element.Element
import javax.lang.model.element.ExecutableElement

class ProcessorHelper(
    private val el: Element,
    private val psiElement: PsiElement
) {

    private val parameters by lazy {
        (psiElement as? KtFunction)?.valueParameters?.map {
            FunctionParameter(
                name = it.name.orEmpty(),
                type = it.typeReference?.text.orEmpty()
            )
        }.orEmpty()
    }

    fun createBook(ktFile: KtFile): BookMetaData? {
        if (isTheSame().not()) return null

        val annotation = el.getAnnotation(UIBook::class.java)
        val method = el as ExecutableElement
        val psiMethod = psiElement as KtNamedFunction

        val function = psiMethod.bodyExpression!!.text

        return BookMetaData(
            name = annotation.name,
            function = function,
            functionName = method.simpleName.toString(),
            packageName = ktFile.packageName,
            parameters = parameters
        )
    }

    private fun isTheSame(): Boolean {
        if (psiElement !is KtFunction) return false
        if (el !is ExecutableElement) return false
        if (el.simpleName.toString() != psiElement.name) return false
        if ((el.parameters.size - 1) != psiElement.valueParameters.size) return false

        return parameters.mapIndexed { index, t ->
            // TODO: more accurate way to check type
            el.parameters[index + 1].asType().toString().contains(t.type)
        }.all { it }
    }
}
