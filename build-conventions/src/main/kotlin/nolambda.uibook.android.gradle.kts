@file:Suppress("UnstableApiUsage")

import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

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

pluginManager.withPlugin("org.jetbrains.kotlin.multiplatform") {
    // https://discuss.kotlinlang.org/t/disabling-androidandroidtestrelease-source-set-in-gradle-kotlin-dsl-script/21448/5
    // Remove log pollution until Android support in KMP improves.
    afterEvaluate {
        project.extensions.findByType<KotlinMultiplatformExtension>()?.let { kmpExt ->
            kmpExt.sourceSets.removeAll {
                setOf(
                    "androidAndroidTestRelease",
                    "androidTestFixtures",
                    "androidTestFixturesDebug",
                    "androidTestFixturesRelease",
                ).contains(it.name)
            }
        }
    }
}
