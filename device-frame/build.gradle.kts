@file:Suppress("UnstableApiUsage")

plugins {
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.multiplatform")
    id("nolambda.uibook.publish")
    id("nolambda.uibook.android")
}

android {
    defaultConfig {
        namespace = "nolambda.uibook.frame"
    }
}

kotlin {
    jvm()
    android {
        publishLibraryVariants("release")
    }
    js(IR) {
        browser()
    }

    sourceSets {
        named("commonMain") {
            dependencies {
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)

                // UI-Book
                api(project(":annotations"))
            }
        }
    }
}
