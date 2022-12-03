plugins {
    kotlin("multiplatform")
}

kotlin {
    jvm {
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }

        compilations.named("main").configure {
            kotlinOptions.jvmTarget = "1.8"
        }
    }
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(project(":annotations"))

                val kotlin_version: String by rootProject
                implementation("org.jetbrains.kotlin:kotlin-compiler-embeddable:$kotlin_version")
                implementation("com.google.devtools.ksp:symbol-processing-api:1.5.31-1.0.0")

                val kotlinPoetVersion = "1.10.2"
                implementation("com.squareup:kotlinpoet:$kotlinPoetVersion")
                implementation("com.squareup:kotlinpoet-ksp:$kotlinPoetVersion")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("com.github.tschuchortdev:kotlin-compile-testing-ksp:1.4.9")
            }
        }
    }
}
