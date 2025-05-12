package zlhywlf.node

import org.gradle.api.Project
import org.gradle.api.file.DirectoryProperty
import org.gradle.kotlin.dsl.property
import zlhywlf.utils.Platform
import zlhywlf.utils.parsePlatform
import zlhywlf.utils.zip

open class NodeExtension(project: Project) {
    private val cacheDir = project.layout.projectDirectory.dir(".gradle")
    val resolvedPlatform = project.objects.property<Platform>()
    val resolvedNodeDir: DirectoryProperty = project.objects.directoryProperty()
    val workDir: DirectoryProperty = project.objects.directoryProperty().convention(cacheDir.dir("nodejs"))
    val version = project.objects.property<String>().convention(DEFAULT_NODE_VERSION)
    val distBaseUrl = project.objects.property<String>().convention(DEFAULT_NODE_DIST_URL)

    companion object {
        const val NAME = "nodePlugin"
        const val DEFAULT_NODE_VERSION = "24.0.1"
        const val DEFAULT_NODE_DIST_URL = "https://nodejs.org/dist"
    }
}

internal fun configureNodeExtension(project: Project, extension: NodeExtension) {
    val platform = parsePlatform(project)
    extension.resolvedPlatform.set(platform)
    val nodeDir = zip(
        extension.workDir,
        extension.version,
    ).map { (workDir, version) -> workDir.dir("node-v$version-${platform.osName}-${platform.osArch}") }
    with(extension.resolvedNodeDir) {
        set(nodeDir)
        finalizeValueOnRead()
    }
}
