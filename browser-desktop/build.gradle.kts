import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("plugin.serialization") version "1.5.10"
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

kotlin {
    jvm()

    sourceSets {
        named("jvmMain") {
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
