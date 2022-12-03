package nolambda.uibook.processors.generator

import com.squareup.kotlinpoet.ClassName
import java.util.*

object TypeMapper {
    fun mapToClassName(type: String): ClassName {
        val javaClassName = convertFromJavaType(type)
        if (javaClassName != null) {
            return javaClassName
        }
        return ClassName("kotlin", type)
    }

    private val javaTypes = listOf(
        "boolean",
        "int",
        "double",
        "float"
    )

    private fun convertFromJavaType(type: String): ClassName? {
        if (type.contains("java.lang", ignoreCase = true)) {
            return ClassName("kotlin", type.substring(type.lastIndexOf(".") + 1))
        }
        if (javaTypes.any { it.equals(type, ignoreCase = true) }) {
            return ClassName("kotlin",
                type.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() })
        }
        return null
    }
}
