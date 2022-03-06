package nolambda.uibook.annotations

import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class UIBook(
    val name: String,
    val viewStateProvider: KClass<*> = Default::class,
    val inputCreator: KClass<*> = Default::class
) {
    object Default
}
