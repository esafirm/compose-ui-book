package nolambda.uibook.processors.utils

import com.google.devtools.ksp.processing.SymbolProcessorEnvironment

class Logger(private val env: SymbolProcessorEnvironment) {
    fun error(message: String) {
        env.logger.error(message)
    }

    fun note(message: String) {
        env.logger.info(message)
    }
}