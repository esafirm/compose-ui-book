package nolambda.uibook.browser.config

import java.util.UUID

actual fun randomResourceId(): String {
    return UUID.randomUUID().toString()
}