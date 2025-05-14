package zlhywlf.plugins.node

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.*
import org.gradle.process.ExecResult
import zlhywlf.utils.GradleHelper

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

    @get:InputFile
    val archiveFile = project.objects.fileProperty()

    @get:Internal
    val gradleHelper = project.objects.newInstance(GradleHelper::class.java)

    init {
        group = "node"
        description = "Setup nodejs"
    }

    @TaskAction
    fun exec() {
        gradleHelper.delete {
            delete(nodeExtension.nodeDir.get().dir("../").asFileTree.matching {
                include("node-v*/**")
            })
        }
        val af = archiveFile.get().asFile
        val nodeDir = nodeExtension.nodeDir
        val nodeBinDir = if (nodeExtension.platform.get().isWindows()) nodeDir else nodeDir.map { it.dir("bin") }
        val archivePath = nodeBinDir.map { it.dir("../") }
        if (af.name.endsWith("zip")) {
            gradleHelper.copy {
                from(gradleHelper.zipTree(af))
                into(archivePath)
            }
        }
    }

    companion object {
        const val NAME = "nodeSetup"
    }
}
