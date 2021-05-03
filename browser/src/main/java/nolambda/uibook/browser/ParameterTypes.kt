package nolambda.uibook.browser

class ParameterTypes {
    companion object {
        const val STRING = "java.lang.String"
        const val INT = "int"
        const val BOOLEAN = "boolean"

        fun getDefaultStateForType(type: String): Any {
            return when (type) {
                STRING -> ""
                INT -> 0
                BOOLEAN -> false
                else -> error("No default value provided for $type")
            }
        }
    }
}