package nolambda.uibook.build.convention

import org.gradle.api.model.ObjectFactory

/**
 * Extension for [org.gradle.api.Project] to provide information about the module.
 */
abstract class UIBookExt(factory: ObjectFactory) {

    val artifactId = factory.property(String::class.java)

    companion object {

        private const val EXT_NAME = "uiBook"

        fun getOrRegister(project: org.gradle.api.Project): UIBookExt {
            return project.extensions.findByType(UIBookExt::class.java)
                ?: project.extensions.create(EXT_NAME, UIBookExt::class.java)
        }
    }
}
