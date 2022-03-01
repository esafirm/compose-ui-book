package nolambda.uibook.factory

interface BookHost

data class BookConfig(
    val onExit: () -> Unit
)