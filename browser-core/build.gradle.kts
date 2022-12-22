plugins {
    kotlin("plugin.serialization")
    kotlin("multiplatform")

    id("org.jetbrains.compose")

    id("nolambda.uibook.android")
    id("nolambda.uibook.publish")
}

android {
    namespace = "nolambda.uibook"
}

kotlin {
    android {
        publishLibraryVariants("release")
    }
    jvm("desktop")
    js(IR) {
        browser()
    }

    sourceSets {
        named("commonMain") {
            dependencies {

                // We should get all compose related dependencies from the plugin
                // compose => ComposePlugin#Dependencies
                // https://github.com/JetBrains/compose-jb/blob/master/gradle-plugins/compose/src/main/kotlin/org/jetbrains/compose/ComposePlugin.kt#L111

                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)

                // Device preview
                api(project(":device-frame"))

                // UI-Book
                api(project(":annotations"))
                api("com.wakaztahir:codeeditor:3.1.0")
                implementation("io.github.irgaly.kottage:kottage:1.4.2")
                implementation("io.github.irgaly.kottage:kottage-core:1.4.2")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
            }
        }
        named("desktopMain") {
            dependencies {
                api(compose.desktop.currentOs)

                // Uncomment if you want to search for icons
                 api(compose.materialIconsExtended)

                // Needed only for preview.
                api(compose.preview)
            }
        }
        named("jsMain") {
            dependencies {
                api(compose.web.core)
            }
        }
    }
}
