package nolambda.uibook.processors.utils

import nolambda.uibook.annotations.FunctionParameter

object DefaultValueResolver {

    fun getDefaultValueForType(type: String): String {
        return when (type) {
            "String", "java.lang.String" -> "This is a text"
            "Int", "int" -> "0"
            "Float", "float" -> "0F"
            "Boolean", "boolean" -> "false"
            else -> error("No default value provided for $type")
        }
    }

    fun createDefaultState(param: FunctionParameter): String {
        return when (param.type) {
            "String", "java.lang.String" -> "\"${param.defaultValue}\""
            else -> param.defaultValue
        }
    }
}