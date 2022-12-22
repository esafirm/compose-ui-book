@file:Suppress("UnstableApiUsage")

import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")

    id("nolambda.uibook.android")
    id("nolambda.uibook.publish")
}

android {
    defaultConfig {
        namespace = "nolambda.uibook.browser.app"
    }
}

kotlin {
    android {
        publishLibraryVariants("release")
    }
    jvm("desktop")
    js(IR) {
        browser()
        binaries.executable()
    }

    sourceSets {
        named("commonMain") {
            dependencies {
                api(project(":annotations"))
                api(project(":browser-core"))
            }
        }
        named("androidMain") {
            dependencies {

                implementation("androidx.core:core-ktx:1.7.0")

                val appCompatVersion = "1.6.1"
                implementation("androidx.activity:activity-compose:$appCompatVersion")

                val coroutineVersion = "1.4.2"
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutineVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutineVersion")
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "UI Book"
            packageVersion = "1.0.0"
        }
    }
}
