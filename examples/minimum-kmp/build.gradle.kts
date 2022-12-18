plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.multiplatform")
    id("org.jetbrains.compose")
    id("com.google.devtools.ksp")
}

kotlin {
    jvm("desktop")
    android()

    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(project(":browser-core"))
                implementation(project(":browser-app"))
            }
        }
        named("androidMain") {
            dependencies {
                implementation("androidx.core:core-ktx:1.7.0")
                implementation("androidx.appcompat:appcompat:1.3.1")
                implementation("com.google.android.material:material:1.4.0")

                // Compose
                implementation("androidx.compose.ui:ui-tooling:1.0.5")
                implementation("androidx.compose.foundation:foundation:1.0.5")
            }
        }
        named("desktopMain") {
            dependencies {
                implementation(compose.desktop.currentOs)
            }
        }
    }
}

android {
    compileSdk = rootProject.ext["compileSdkVersion"] as Int

    defaultConfig {
        applicationId = "nolambda.uibook.sample"
        minSdk = rootProject.ext.get("minSdkVersion") as Int
        targetSdk = rootProject.ext.get("targetSdkVersion") as Int
        versionCode = 1
        versionName = "1.0"
    }

    sourceSets {
        named("main") {
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
            res.srcDirs("src/androidMain/res")
        }
    }
}

compose.desktop {
    application {
        mainClass = "nolambda.uibook.browser.desktop.MainKt"

        nativeDistributions {
            targetFormats(
                org.jetbrains.compose.desktop.application.dsl.TargetFormat.Dmg,
                org.jetbrains.compose.desktop.application.dsl.TargetFormat.Msi,
                org.jetbrains.compose.desktop.application.dsl.TargetFormat.Deb
            )
            packageName = "UIBook"
            packageVersion = "1.0.0"
        }
    }
}

dependencies {
    val processorDep = "io.github.esafirm.uibook:annotations-processor:${rootProject.version}"
    add("kspAndroid", processorDep)
    add("kspDesktop", processorDep)
}
