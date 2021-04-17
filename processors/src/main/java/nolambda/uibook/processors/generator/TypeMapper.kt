package nolambda.uibook.processors.generator

import com.squareup.kotlinpoet.ClassName

object TypeMapper {
    fun mapToClassName(type: String): ClassName {
        val javaClassName = convertJavaType(type)
        if (javaClassName != null) {
            return javaClassName
        }
        error("Can't handle type: $type")
    }

    private val javaTypes = listOf(
        "boolean",
        "int",
        "double",
        "float"
    )

    private fun convertJavaType(type: String): ClassName? {
        if (type.contains("java.lang", ignoreCase = true)) {
            return ClassName("kotlin", type.substring(type.lastIndexOf(".") + 1))
        }
        if (javaTypes.any { it.equals(type, ignoreCase = true) }) {
            return ClassName("kotlin", type)
        }
        return null
    }
}