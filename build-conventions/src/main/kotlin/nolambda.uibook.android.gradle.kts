@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.library")
}

android {
    defaultConfig {
        minSdk = rootProject.extra["minSdkVersion"] as Int
        targetSdk = rootProject.extra["targetSdkVersion"] as Int
        compileSdk = rootProject.extra["compileSdkVersion"] as Int
    }
    sourceSets {
        named("main") {
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
            res.srcDir("src/androidMain/res")
        }
    }
}
