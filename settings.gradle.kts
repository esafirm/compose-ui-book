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

plugins {
    id("com.gradle.enterprise") version "3.8.1"
}

gradleEnterprise {
    buildScan {
        publishAlways()
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
            setUrl("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        }
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/esafirm/compose-code-editor")
            credentials {
                username = secretProps["github.username"] as String
                password = secretProps["github.token"] as String
            }
        }
    }
}

rootProject.name = "UIBook"

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
