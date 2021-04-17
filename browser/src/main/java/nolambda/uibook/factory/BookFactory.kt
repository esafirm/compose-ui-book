package nolambda.uibook.factory

import android.content.Context
import android.view.View
import nolambda.uibook.annotations.BookMetaData

interface BookFactory {
    fun getView(context: Context): View
    fun getMetaData(): BookMetaData
}