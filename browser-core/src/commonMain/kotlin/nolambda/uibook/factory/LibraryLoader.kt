package nolambda.uibook.factory

/**
 * Load the UI Book library that already captured by the annotation processor.
 */
interface LibraryLoader {
    fun load(): UIBookLibrary
}
