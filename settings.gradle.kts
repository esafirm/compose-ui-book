@file:Suppress("UnstableApiUsage")

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven {
            setUrl("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        }
    }
}

val secretProps = java.util.Properties()
file("secret.properties").inputStream().use { secretProps.load(it) }

dependencyResolutionManagement {
    rulesMode.set(RulesMode.FAIL_ON_PROJECT_RULES)

    repositories {
        google()
        mavenCentral()
        mavenLocal()
        maven { setUrl("https://jitpack.io") }
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

include(":browser-android")
include(":browser-core")
include(":browser-desktop")
include(":annotations")
include(":annotations-processor")
include(":device-frame")
include(":sample")

// Gradle plugins for build conventions
includeBuild("build-conventions")
