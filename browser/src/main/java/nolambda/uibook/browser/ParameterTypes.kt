package nolambda.uibook.browser

class ParameterTypes {
    companion object {
        const val STRING = "java.lang.String"
        const val INT = "int"
        const val FLOAT = "float"
        const val BOOLEAN = "boolean"

        fun isNumber(type: String): Boolean {
            return type == INT || type == FLOAT
        }

        fun getDefaultStateForType(type: String): Any {
            return when (type) {
                STRING -> ""
                INT -> 0
                FLOAT -> 0F
                BOOLEAN -> false
                else -> error("No default value provided for $type")
            }
        }
    }
}