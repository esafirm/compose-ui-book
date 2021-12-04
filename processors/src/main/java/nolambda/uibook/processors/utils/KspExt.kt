package nolambda.uibook.processors.utils.sourcelocator

import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.*

/**
 * Finds the class that contains this declaration and throws [IllegalStateException] if it cannot
 * be found.
 * @see [findEnclosingAncestorClassDeclaration]
 */
//internal fun KSDeclaration.requireEnclosingMemberContainer(
//    env: SymbolProcessorEnvironment
//): KSDeclaration {
//    return checkNotNull(findEnclosingMemberContainer(env)) {
//        "Cannot find required enclosing type for $this"
//    }
//}

fun KSDeclaration.findEnclosingAncestorClassDeclaration(): KSClassDeclaration? {
    var parent = parentDeclaration
    while (parent != null && parent !is KSClassDeclaration) {
        parent = parent.parentDeclaration
    }
    return parent as? KSClassDeclaration
}