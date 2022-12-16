import nolambda.uibook.build.convention.UIBookExt
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.`maven-publish`
import org.gradle.kotlin.dsl.signing
import java.util.Base64
import java.util.Properties

plugins {
    `maven-publish`
    signing
}

val secretPropsFile = project.rootProject.file("local.properties")
if (secretPropsFile.exists()) {
    secretPropsFile.reader().use {
        Properties().apply {
            load(it)
        }
    }.onEach { (name, value) ->
        extra[name.toString()] = value
    }
} else {
    extra["signing.password"] = System.getenv("SIGNING_PASSWORD")
    extra["signing.key"] = System.getenv("SINGING_KEY")
    extra["ossrhUsername"] = System.getenv("OSSRH_USERNAME")
    extra["ossrhPassword"] = System.getenv("OSSRH_PASSWORD")
}

val sourceJar by tasks.registering(Jar::class) {
    archiveClassifier.set("source")
}

val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
}

val signingKey: String
    get() {
        val base64encodedKey = extra["signing.key"] as String
        return String(Base64.getDecoder().decode(base64encodedKey))
    }

val uiBookExt = UIBookExt.getOrRegister(project)

publishing {

    signing {
        useInMemoryPgpKeys(
            signingKey,
            extra["signing.password"] as String
        )
        sign(publishing.publications)
    }

    repositories {
        maven {
            name = "sonatype"
            setUrl("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = extra["ossrhUsername"]?.toString() ?: error("ossrh username is not set")
                password = extra["ossrhPassword"]?.toString() ?: error("ossrh password is not set")
            }
        }
    }

    // Configure all publications
    publications.withType<MavenPublication> {

        // Configure the publication
        artifactId = uiBookExt.artifactId.getOrElse(project.name)
        groupId = rootProject.group.toString()
        version = rootProject.version.toString()

        // Stub javadoc.jar artifact needed by sonatype closing process
        artifact(javadocJar.get())

        // Stub source.jar artifact
        artifact(sourceJar.get())

        // Provide artifacts information requited by Maven Central
        pom {
            name.set("Compose UI Book")
            description.set("UI Component explorer for Jetpack Compose")
            url.set("https://github.com/esafirm/compose-ui-book")

            licenses {
                license {
                    name.set("MIT")
                    url.set("https://opensource.org/licenses/MIT")
                }
            }
            developers {
                developer {
                    id.set("esafirm")
                    name.set("Esa Firman")
                    email.set("esafirm21@gmail.com")
                }
            }
            scm {
                url.set("https://github.com/esafirm/compose-ui-book")
            }

        }
    }
}
