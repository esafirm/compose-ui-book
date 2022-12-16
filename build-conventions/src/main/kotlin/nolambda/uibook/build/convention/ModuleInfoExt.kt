package nolambda.uibook.build.convention

import org.gradle.api.model.ObjectFactory

/**
 * Extension for [org.gradle.api.Project] to provide information about the module.
 */
abstract class UIBookExt(factory: ObjectFactory) {

    val artifactId = factory.property(String::class.java)

    companion object {
        fun register(project: org.gradle.api.Project): UIBookExt {
            return project.extensions.create("uiBook", UIBookExt::class.java)
        }
    }
}
