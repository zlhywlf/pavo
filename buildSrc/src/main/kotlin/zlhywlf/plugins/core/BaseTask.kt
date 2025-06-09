package zlhywlf.plugins.core

import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.Internal
import zlhywlf.models.Platform

abstract class BaseTask<E : BaseExtension>(
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
) : DefaultTask() {
    @get:Internal
    protected val gradleHelper: GradleHelper = project.objects.newInstance(GradleHelper::class.java)

    @get:InputFile
    protected val archiveFile: RegularFileProperty = project.objects.fileProperty()

    @get:Internal
    protected val ext: E by lazy {
        getExtension()
    }

    init {
        enabled = ext.download.get()
        if (enabled) {
            configure()
        }
    }

    protected fun configure() {
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

    @Internal
    internal abstract fun getExtension(): E
}
