package nolambda.uibook.factory

import android.content.Context
import android.view.View
import nolambda.uibook.annotations.BookMetaData

interface BookFactory {
    fun getBook(config: BookConfig): View
    fun getMetaData(): BookMetaData
}