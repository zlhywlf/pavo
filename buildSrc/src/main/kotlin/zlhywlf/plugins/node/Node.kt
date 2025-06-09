package zlhywlf.plugins.node

import org.gradle.api.Project
import org.gradle.api.file.DirectoryProperty
import org.gradle.kotlin.dsl.*
import zlhywlf.plugins.core.BaseExtension
import zlhywlf.utils.PlatformUtil
import zlhywlf.models.Platform

class NodeExtension(project: Project) : BaseExtension(project) {
    val version = project.objects.property<String>().convention(DEFAULT_NODE_VERSION)
    val distUrl = project.objects.property<String>().convention(DEFAULT_NODE_DIST_URL)

    val platform = project.objects.property<Platform>()
    val workDir: DirectoryProperty = project.objects.directoryProperty().convention(cacheDir.dir(DEFAULT_WORK_DIR_NAME))
    val nodeDir: DirectoryProperty = project.objects.directoryProperty()

    companion object {
        private const val DEFAULT_NODE_VERSION = "24.0.1"
        private const val DEFAULT_NODE_DIST_URL = "https://nodejs.org/dist"
        private const val DEFAULT_WORK_DIR_NAME = "nodejs"
    }
}

internal fun configureNodeExtension(project: Project, ext: NodeExtension) {
    val platform = PlatformUtil.parsePlatform(project)
    ext.platform.set(platform)
    with(ext.nodeDir) {
        set(ext.workDir.flatMap { w -> ext.version.map { v -> w.dir("node-v$v-${platform.osName}-${platform.osArch}") } })
        finalizeValueOnRead()
    }
}

internal fun addTasks(project: Project) {

}
