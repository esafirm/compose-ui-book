plugins {
    id("com.android.application")
    id("org.jetbrains.compose")
    id("com.google.devtools.ksp")
    kotlin("android")
}

android {
    compileSdk = rootProject.ext["compileSdkVersion"] as Int

    buildFeatures {
        viewBinding = true
    }

    defaultConfig {
        applicationId = "nolambda.uibook.sample"
        minSdk = rootProject.ext.get("minSdkVersion") as Int
        targetSdk = rootProject.ext.get("targetSdkVersion") as Int
        versionCode = 1
        versionName = "1.0"
    }
}

dependencies {
//    implementation("io.github.esafirm.uibook:browser-app:${rootProject.version}")
    implementation(project(":browser-app"))
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("com.google.android.material:material:1.4.0")

    val processorDep = "io.github.esafirm.uibook:annotations-processor:${rootProject.version}"
    ksp(project(":annotations-processor"))
}
