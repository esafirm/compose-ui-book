package nolambda.uibook.annotations

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.SOURCE)
annotation class State(
    val defaultValue: String
)
