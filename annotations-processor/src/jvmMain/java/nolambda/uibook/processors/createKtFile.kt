package nolambda.uibook.processors

import nolambda.uibook.processors.utils.SourceCodeLocator
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys
import org.jetbrains.kotlin.cli.common.messages.MessageRenderer.PLAIN_RELATIVE_PATHS
import org.jetbrains.kotlin.cli.common.messages.PrintingMessageCollector
import org.jetbrains.kotlin.cli.jvm.compiler.EnvironmentConfigFiles
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment
import org.jetbrains.kotlin.com.intellij.openapi.util.Disposer
import org.jetbrains.kotlin.com.intellij.psi.PsiManager
import org.jetbrains.kotlin.com.intellij.testFramework.LightVirtualFile
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.idea.KotlinFileType
import org.jetbrains.kotlin.psi.KtFile

// Added by suggestion in here
// https://github.com/facebookincubator/ktfmt/commit/b7aa2ad734c563819bb2970bc6e8ac08bdb6b848
private fun getConfiguration() = CompilerConfiguration().apply {
    put(
        CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY,
        PrintingMessageCollector(System.err, PLAIN_RELATIVE_PATHS, false)
    )
}

private val project by lazy {
    KotlinCoreEnvironment.createForProduction(
        Disposer.newDisposable(),
        getConfiguration(),
        EnvironmentConfigFiles.JVM_CONFIG_FILES
    ).project
}

fun SourceCodeLocator.SourceCodeResult.createKtFile(): KtFile {
    try {
        return PsiManager.getInstance(project)
            .findFile(
                LightVirtualFile(fileName, KotlinFileType.INSTANCE, sourceCode)
            ) as KtFile
    } catch (e: Exception) {
        error("Get error of $e")
    }
}
