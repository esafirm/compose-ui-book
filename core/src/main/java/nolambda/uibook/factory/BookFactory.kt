package nolambda.uibook.factory

import nolambda.uibook.annotations.BookMetaData

interface BookFactory<T> {
    fun getBook(config: BookConfig): T
    fun getMetaData(): BookMetaData
}