package zlhywlf.plugins.node

import org.gradle.api.DefaultTask
import org.gradle.api.file.Directory
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Provider
import org.gradle.api.provider.ProviderFactory
import org.gradle.api.tasks.*
import org.gradle.process.ExecResult
import javax.inject.Inject
import zlhywlf.utils.GradleHelper
import java.nio.file.Path
import java.nio.file.Files
import java.nio.file.Paths

abstract class BaseTask : DefaultTask() {
    @get:Internal
    var result: ExecResult? = null

    @get:Internal
    internal val util by lazy {
        Util()
    }
}


abstract class NodeSetupTask : BaseTask() {
    private val nodeExtension = NodeExtension[project]

    @get:Input
    val download = nodeExtension.download

    @get:Inject
    abstract val objects: ObjectFactory

    @get:Inject
    abstract val providers: ProviderFactory

//    @get:InputFile
//    val nodeArchiveFile = objects.fileProperty()

//    @get:OutputDirectory
//    abstract val nodeDir: DirectoryProperty

    @get:Internal
    val gradleHelper = project.objects.newInstance(GradleHelper::class.java)

    init {
        group = "node"
        description = "Setup nodejs"
    }

    @TaskAction
    fun exec() {
        if (download.get()) {
        }
        println(download.get())
        println(objects)
        println(project.objects)
        println(project.objects.fileProperty().orNull?.asFile?.name)
    }

    companion object {
        const val NAME = "nodeSetup"
    }
}
