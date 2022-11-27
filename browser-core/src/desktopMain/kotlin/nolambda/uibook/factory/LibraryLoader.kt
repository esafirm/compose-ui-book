package nolambda.uibook.factory

import nolambda.uibook.annotations.UIBookCons

class DesktopLibraryLoader : LibraryLoader {
    override fun load(): UIBookLibrary {
        val clazz = Class.forName(UIBookCons.LIBRARY_CLASS)
        return clazz.newInstance() as UIBookLibrary
    }
}
