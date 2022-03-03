package nolambda.uibook.factory

import kotlin.collections.List

public class UIBookLibraryImpl : UIBookLibrary {
  public override fun getBookFactories(): List<BookFactory> {
    // List all book factories in here
    return listOf(
      ComposeCodeBookFactory(),
    )
  }
}
