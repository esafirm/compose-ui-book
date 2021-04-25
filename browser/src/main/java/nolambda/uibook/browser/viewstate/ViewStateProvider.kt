package nolambda.uibook.browser.viewstate

import nolambda.uibook.annotations.BookMetaData
import nolambda.uibook.browser.form.ViewState

interface ViewStateProvider {
    fun createViewState(book: BookMetaData): ViewState
}