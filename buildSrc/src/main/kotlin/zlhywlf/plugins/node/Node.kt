package zlhywlf.plugins.node

import org.gradle.api.Project
import org.gradle.kotlin.dsl.*

open class NodeExtension(project: Project) {
    private val cacheDir = project.layout.projectDirectory.dir(CACHE_DIR)
    val version = project.objects.property<String>().convention(DEFAULT_NODE_VERSION)
    val distUrl = project.objects.property<String>().convention(DEFAULT_NODE_DIST_URL)

    companion object {
        private const val CACHE_DIR = ".gradle"
        private const val DEFAULT_NODE_VERSION = "24.0.1"
        private const val DEFAULT_NODE_DIST_URL = "https://nodejs.org/dist"
    }
}
