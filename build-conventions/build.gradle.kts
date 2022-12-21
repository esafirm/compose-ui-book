plugins {
    `kotlin-dsl` // Is needed to turn our build logic written in Kotlin into Gralde Plugin
}

repositories {
    gradlePluginPortal() // To use 'maven-publish' and 'signing' plugins in our own plugin
    google()
}

dependencies {
    api("com.android.tools.build:gradle:7.3.1")
    compileOnly("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.20")
}
