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

                val kotlinVersion: String by rootProject
                implementation("org.jetbrains.kotlin:kotlin-compiler-embeddable:$kotlinVersion")
                implementation("com.google.devtools.ksp:symbol-processing-api:1.7.20-1.0.8")

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
