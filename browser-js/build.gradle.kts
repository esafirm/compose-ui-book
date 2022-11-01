import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("plugin.serialization") version "1.5.10"
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

kotlin {
     js(IR) {
        browser()
        binaries.executable()
    }

    sourceSets {
        named("jsMain") {
            dependencies {
                implementation(compose.web.core)
                implementation(project(":browser-core"))
            }
        }
    }
}

compose.experimental {
    web.application {}
}
