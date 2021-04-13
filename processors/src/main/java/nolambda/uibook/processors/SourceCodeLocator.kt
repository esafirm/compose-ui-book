package nolambda.uibook.processors

import java.io.ByteArrayInputStream
import java.io.File
import java.io.ObjectInputStream
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.element.ExecutableElement

internal class SourceCodeLocator(private val logger: Logger) {

    companion object {
        private const val KAPT_METADATA_EXTENSION = "kapt_metadata"
        private const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }

    fun sourceOf(function: Element, environment: ProcessingEnvironment): SourceCodeResult {
        if (function !is ExecutableElement)
            error("Cannot extract source code from non-executable element")

        return getSourceCodeContainingFunction(function, environment)
    }

    private fun getSourceCodeContainingFunction(
        function: Element,
        environment: ProcessingEnvironment
    ): SourceCodeResult {
        val metadataFile = getMetadataForFunction(function, environment)
        val path = deserializeMetadata(metadataFile.readBytes()).entries
            .single { it.key.contains(function.simpleName) }
            .value

        val sourceFile = File(path)
        assert(sourceFile.isFile) { "Source file does not exist at stated position within metadata" }

        return SourceCodeResult(
            sourceCode = sourceFile.readText(),
            fileName = sourceFile.name,
            path = path
        )
    }

    private fun getMetadataForFunction(element: Element, environment: ProcessingEnvironment): File {
        val enclosingClass = element.enclosingElement
        assert(enclosingClass.kind == ElementKind.CLASS)

        val stubDirectory = locateStubDirectory(environment)
        val metadataPath = enclosingClass.toString().replace(".", "/")
        val metadataFile = File("$stubDirectory/$metadataPath.$KAPT_METADATA_EXTENSION")

        logger.note(
            """
            Meta path: $metadataPath
            Meta file: $metadataFile
        """.trimIndent()
        )

        if (!metadataFile.isFile) error("Cannot locate kapt metadata for function")
        return metadataFile
    }

    private fun deserializeMetadata(data: ByteArray): Map<String, String> {
        val metadata = mutableMapOf<String, String>()

        val ois = ObjectInputStream(ByteArrayInputStream(data))
        ois.readInt() // Discard version information

        val lineInfoCount = ois.readInt()
        repeat(lineInfoCount) {
            val fqName = ois.readUTF()
            val path = ois.readUTF()
            val isRelative = ois.readBoolean()
            ois.readInt() // Discard position information

            assert(!isRelative)

            metadata[fqName] = path
        }

        return metadata
    }

    private fun locateStubDirectory(environment: ProcessingEnvironment): File {
        val kaptKotlinGeneratedDir = environment.options[KAPT_KOTLIN_GENERATED_OPTION_NAME]
        val buildDirectory =
            File(kaptKotlinGeneratedDir).ancestors.firstOrNull { it.name == "build" }
        val stubDirectory =
            buildDirectory?.let { File("${buildDirectory.path}/tmp/kapt3/stubs/debug") }

        logger.note("Kapt generated dir: $kaptKotlinGeneratedDir")
        logger.note("Build dir: $buildDirectory")
        logger.note("Stub: $stubDirectory")

        if (stubDirectory == null || !stubDirectory.isDirectory)
            error("Could not locate kapt stub directory")

        return stubDirectory
    }

    // TODO: convert into generator for Kotlin 1.3
    private val File.ancestors: Iterable<File>
        get() {
            val ancestors = mutableListOf<File>()
            var currentAncestor: File? = this

            while (currentAncestor != null) {
                ancestors.add(currentAncestor)
                currentAncestor = currentAncestor.parentFile
            }

            return ancestors
        }

    data class SourceCodeResult(
        val sourceCode: String,
        val fileName: String,
        val path: String
    )
}