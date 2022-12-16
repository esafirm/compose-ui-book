pluginManagement {
    repositories {
        gradlePluginPortal()
        maven {
            setUrl("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        }
    }
}

rootProject.name = "UI Book"

include(":browser-android")
include(":browser-core")
include(":browser-desktop")
include(":annotations")
include(":annotations-processor")
include(":device-frame")
include(":sample")

// Gradle plugins for build conventions
includeBuild("build-conventions")
