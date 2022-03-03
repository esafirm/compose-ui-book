package nolambda.uibook.browser

class ParameterTypes {
    companion object {
        const val STRING = "String"
        const val INT = "Int"
        const val FLOAT = "Float"
        const val BOOLEAN = "Boolean"

        fun isNumber(type: String): Boolean {
            return type == INT || type == FLOAT
        }
    }
}