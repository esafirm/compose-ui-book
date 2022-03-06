package nolambda.uibook.factory

import nolambda.uibook.annotations.BookMetaData
import nolambda.uibook.browser.form.ComposeEmitter

interface BookFactory {
    fun getBook(config: BookConfig): ComposeEmitter
    fun getMetaData(): BookMetaData
}