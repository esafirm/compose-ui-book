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
    }
}