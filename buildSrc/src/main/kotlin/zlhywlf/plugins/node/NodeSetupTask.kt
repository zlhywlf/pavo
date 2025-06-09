package zlhywlf.plugins.node

import org.gradle.api.tasks.TaskAction
import zlhywlf.plugins.core.BaseTask
import zlhywlf.utils.ExtensionUtil

abstract class NodeSetupTask : BaseTask() {
    private val ext = ExtensionUtil.getOrCreate<NodeExtension>(project)

    init {
        group = "node"
        description = "Setup nodejs"
        enabled = ext.download.get()
        if (enabled) {
            configure()
        }
    }

    companion object {
        const val NAME = "nodeSetup"
    }

    @TaskAction
    fun exec() {
        gradleHelper.delete {
            delete(ext.sdkDir.get())
        }
        println(archiveFile.get())
    }

    private fun configure() {
        ext.distUrl.orNull?.let {
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
            }
        }
        val osName = ext.platform.get().osName
        val osArch = ext.platform.get().osArch
        val nodeExt = if (ext.platform.get().isWindows()) "zip" else "tar.gz"
        val depNameProvider = ext.version.map { v -> "org.nodejs:node:$v:$osName-$osArch@$nodeExt" }
        val archiveFileProvider = depNameProvider.map {
            val dep = project.dependencies.create(it)
            val configuration = project.configurations.detachedConfiguration(dep)
            configuration.isTransitive = false
            configuration.resolve().single()
        }
        archiveFile.set(project.layout.file(archiveFileProvider))
    }
}
