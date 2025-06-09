package zlhywlf.plugins.python

import zlhywlf.plugins.core.BaseTask
import org.gradle.api.tasks.TaskAction
import zlhywlf.utils.ExtensionUtil

abstract class PythonSetupTask : BaseTask() {
    private val ext = ExtensionUtil.getOrCreate<PythonExtension>(project)

    init {
        group = "python"
        description = "Setup python"
        enabled = ext.download.get()
        if (enabled) {
            configure()
        }
    }

    companion object {
        const val NAME = "pythonSetup"
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
                name = "python"
                setUrl(it)
                patternLayout {
                    artifact("[revision]/[artifact](-[revision]).[ext]")
                }
                metadataSources {
                    artifact()
                }
                content {
                    includeModule("org.python", "python")
                }
            }
        }
        val pythonExt = if (ext.platform.get().isWindows()) "exe" else "tgz"
        val depNameProvider = ext.version.map { v -> "org.python:python:$v:@$pythonExt" }
        val archiveFileProvider = depNameProvider.map {
            val dep = project.dependencies.create(it)
            val configuration = project.configurations.detachedConfiguration(dep)
            configuration.isTransitive = false
            configuration.resolve().single()
        }
        archiveFile.set(project.layout.file(archiveFileProvider))
    }
}
