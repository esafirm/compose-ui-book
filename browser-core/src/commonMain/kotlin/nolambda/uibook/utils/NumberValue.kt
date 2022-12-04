package nolambda.uibook.utils

/**
 * Convert a [Number] to a correct [Number] type
 */
fun Number.asNumberType(type: String): Number {
    return when (type) {
        "Int" -> this.toInt()
        "Long" -> this.toLong()
        else -> this
    }
}
