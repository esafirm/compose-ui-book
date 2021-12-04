package nolambda.uibook.processors.utils

import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import java.io.File

class SourceCodeLocator(
    private val logger: Logger
) {

    fun sourceOf(function: KSFunctionDeclaration): SourceCodeResult {
        val containingFile = function.containingFile ?: error("No containing file available")
        val path = containingFile.filePath

        val sourceFile = File(path)
        assert(sourceFile.isFile) { "Source file does not exist at stated position within metadata" }

        logger.note("Source code located in: $path")

        return SourceCodeResult(
            sourceCode = sourceFile.readText(),
            fileName = sourceFile.name,
            path = path
        )
    }

    data class SourceCodeResult(
        val sourceCode: String,
        val fileName: String,
        val path: String
    )
}