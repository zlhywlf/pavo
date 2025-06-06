package zlhywlf.plugins.node

import org.gradle.api.Project
import org.gradle.api.file.DirectoryProperty
import org.gradle.kotlin.dsl.*
import zlhywlf.utils.Platform
import zlhywlf.utils.parsePlatform
import zlhywlf.utils.zip

open class NodeExtension(project: Project) {
    private val cacheDir = project.layout.projectDirectory.dir(".gradle")
    val platform = project.objects.property<Platform>()
    val nodeDir: DirectoryProperty = project.objects.directoryProperty()
    val workDir: DirectoryProperty = project.objects.directoryProperty().convention(cacheDir.dir("nodejs"))
    val allowInsecureProtocol = project.objects.property<Boolean>()
    val version = project.objects.property<String>().convention(DEFAULT_NODE_VERSION)
    val distUrl = project.objects.property<String>().convention(DEFAULT_NODE_DIST_URL)
    val download = project.objects.property<Boolean>().convention(false)

    companion object {
        private const val NAME = "nodePlugin"
        const val DEFAULT_NODE_VERSION = "24.0.1"
        const val DEFAULT_NODE_DIST_URL = "https://nodejs.org/dist"

        @JvmStatic
        fun create(project: Project): NodeExtension {
            return project.extensions.create(NAME, project)
        }

        @JvmStatic
        operator fun get(project: Project): NodeExtension {
            return project.extensions.getByType()
        }
    }
}

internal fun configureNodeExtension(project: Project, extension: NodeExtension) {
    val platform = parsePlatform(project)
    extension.platform.set(platform)
    val nodeDir = zip(
        extension.workDir,
        extension.version,
    ).map { (workDir, version) -> workDir.dir("node-v$version-${platform.osName}-${platform.osArch}") }
    with(extension.nodeDir) {
        set(nodeDir)
        finalizeValueOnRead()
    }
}

internal fun configureNodeSetupTask(project: Project, extension: NodeExtension) {
    project.tasks.withType<NodeSetupTask>().configureEach {
        onlyIf {
            extension.download.get()
        }
        if (extension.download.get()) {
            extension.distUrl.orNull?.let {
                project.repositories.ivy {
                    name = "Node.js"
                    setUrl(it)
                    patternLayout {
                        artifact("v[revision]/[artifact](-v[revision]-[classifier]).[ext]")
                    }
                    metadataSources {
                        artifact()
                    }
                    content {
                        includeModule("org.nodejs", "node")
                    }
                    extension.allowInsecureProtocol.orNull?.let {
                        isAllowInsecureProtocol = it
                    }
                }
            }
            val osName = extension.platform.get().osName
            val osArch = extension.platform.get().osArch
            val type = if (extension.platform.get().isWindows()) "zip" else "tar.gz"
            val dep = extension.version.map { version -> "org.nodejs:node:$version:$osName-$osArch@$type" }
            val archiveFileProvider = dep.map {
                val dependency = project.dependencies.create(it)
                val configuration = project.configurations.detachedConfiguration(dependency)
                configuration.isTransitive = false
                configuration.resolve().single()
            }
            archiveFile.set(project.layout.file(archiveFileProvider))
        }
    }
}

internal fun addTasks(project: Project) {
    project.tasks.register<NodeSetupTask>(NodeSetupTask.NAME)
}
