plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("nolambda.uibook.publish")
}

kotlin {
    jvm {
        withJava()
    }
}
