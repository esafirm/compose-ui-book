@file:Suppress("UnstableApiUsage")

import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("plugin.serialization") version "1.5.10"
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

    sourceSets {
        named("androidMain") {
            dependencies {
                api(project(":annotations"))
                api(project(":browser-core"))

                implementation("androidx.core:core-ktx:1.7.0")

                val appCompatVersion = "1.6.1"
                implementation("androidx.activity:activity-compose:$appCompatVersion")

                val coroutineVersion = "1.4.2"
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutineVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutineVersion")
            }
        }
        named("desktopMain") {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(project(":browser-core"))
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
