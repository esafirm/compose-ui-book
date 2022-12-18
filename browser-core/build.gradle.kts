plugins {
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.multiplatform")

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

    sourceSets {
        named("commonMain") {
            dependencies {

                // We should get all compose related dependencies from the plugin
                // compose => ComposePlugin#Dependencies
                // https://github.com/JetBrains/compose-jb/blob/master/gradle-plugins/compose/src/main/kotlin/org/jetbrains/compose/ComposePlugin.kt#L111

                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)
                api(compose.materialIconsExtended)

                // Needed only for preview.
                implementation(compose.preview)

                // Device preview
                api(project(":device-frame"))

                // UI-Book
                api(project(":annotations"))
                api("com.wakaztahir:codeeditor:3.0.5")
            }
        }
    }
}
