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

    sourceSets {
        named("commonMain") {
            dependencies {
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)
                api(compose.materialIconsExtended)

                // Needed only for preview.
                implementation(compose.preview)

                // UI-Book
                api(project(":annotations"))
            }
        }
    }
}
