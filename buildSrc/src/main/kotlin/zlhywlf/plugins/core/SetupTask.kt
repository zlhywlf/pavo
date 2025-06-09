package zlhywlf.plugins.core

import org.gradle.api.tasks.Internal
import zlhywlf.models.Platform

abstract class SetupTask<E : BaseExtension>(
    @get:Internal
    val classifierName: (Platform) -> String,
    @get:Internal
    val groupName: String,
    @get:Internal
    val artifactName: String,
    @get:Internal
    val artifactPattern: String,
    @get:Internal
    val extName: Pair<String, String>
) : BaseTask<E>() {

    init {
        enabled = ext.download.get()
        if (enabled) {
            configure()
        }
    }

    private fun configure() {
        ext.distUrl.orNull?.let {
            project.repositories.ivy {
                name = artifactName
                setUrl(it)
                patternLayout {
                    artifact(artifactPattern)
                }
                metadataSources {
                    artifact()
                }
                content {
                    includeModule(groupName, artifactName)
                }
            }
        }
        val pythonExt = if (ext.platform.get().isWindows()) extName.first else extName.second
        val depNameProvider =
            ext.version.map { v -> "$groupName:$artifactName:$v:${classifierName(ext.platform.get())}@$pythonExt" }
        val archiveFileProvider = depNameProvider.map {
            val dep = project.dependencies.create(it)
            val configuration = project.configurations.detachedConfiguration(dep)
            configuration.isTransitive = false
            configuration.resolve().single()
        }
        archiveFile.set(project.layout.file(archiveFileProvider))
    }
}
