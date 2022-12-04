package nolambda.uibook.annotations

import kotlin.reflect.KClass

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.SOURCE)
annotation class State(
    val defaultValue: String,
    val inputCreator: KClass<*> = UIBook.Default::class
)
