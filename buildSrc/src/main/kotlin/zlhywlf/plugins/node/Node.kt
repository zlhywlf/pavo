package zlhywlf.plugins.node

import org.gradle.api.Project
import org.gradle.kotlin.dsl.*
import zlhywlf.plugins.core.BaseExtension

class NodeExtension(project: Project) : BaseExtension(project) {
    val version = project.objects.property<String>().convention(DEFAULT_NODE_VERSION)
    val distUrl = project.objects.property<String>().convention(DEFAULT_NODE_DIST_URL)

    companion object {
        private const val DEFAULT_NODE_VERSION = "24.0.1"
        private const val DEFAULT_NODE_DIST_URL = "https://nodejs.org/dist"
    }
}
