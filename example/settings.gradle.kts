pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google()
    }
}

include(":common", ":android", ":desktop")

fun includeUiBook(name: String) {
    include(name)
    project(name).projectDir = file("../${name.replace(":", "")}")
}

includeUiBook(":browser")
includeUiBook(":browser-desktop")
includeUiBook(":browser-core")
includeUiBook(":annotations")
includeUiBook(":processors")
