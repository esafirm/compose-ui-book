package nolambda.uibook.processors

import javax.lang.model.type.MirroredTypeException
import javax.lang.model.type.TypeMirror

fun getTypeMirror(block: () -> Unit): TypeMirror {
    try {
        block.invoke()
        error("Block did not result on exception!")
    } catch (e: MirroredTypeException) {
        return e.typeMirror
    }
}