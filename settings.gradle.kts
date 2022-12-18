@file:Suppress("UnstableApiUsage")

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        maven {
            setUrl("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        }
    }
}

val secretProps = java.util.Properties()
file("secret.properties").inputStream().use { secretProps.load(it) }

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        mavenLocal()
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/qawaz/compose-code-editor")
            credentials {
                username = secretProps["github.username"] as String
                password = secretProps["github.token"] as String
            }
        }
    }
}

rootProject.name = "UI Book"

include(":browser-app")
include(":browser-core")
include(":annotations")
include(":annotations-processor")
include(":device-frame")

// Examples
include(":examples:demo")
include(":examples:minimum-kmp")
include(":examples:minimum-android")

// Gradle plugins for build conventions
includeBuild("build-conventions")
