@file:Suppress("UnstableApiUsage")

plugins {
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.multiplatform")
    id("com.android.library")
}

android {
    defaultConfig {
        namespace = "nolambda.uibook.deviceframe"
        minSdk = rootProject.ext["minSdkVersion"] as Int
        targetSdk = rootProject.ext["targetSdkVersion"] as Int
        compileSdk = rootProject.ext["compileSdkVersion"] as Int
    }
}

kotlin {
    android()
    jvm("desktop")

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
