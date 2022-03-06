package nolambda.uibook.factory

internal interface LibraryLoaderInterface {
    fun load(): UIBookLibrary
}

expect object LibraryLoader : LibraryLoaderInterface