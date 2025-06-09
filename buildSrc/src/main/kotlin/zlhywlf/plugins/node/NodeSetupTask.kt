package zlhywlf.plugins.node

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import zlhywlf.utils.ExtensionUtil

abstract class NodeSetupTask : DefaultTask() {
    private val ext = ExtensionUtil.getOrCreate<NodeExtension>(project)

    init {
        group = "node"
        description = "Setup nodejs"
    }

    companion object {
        const val NAME = "nodeSetup"
    }

    @TaskAction
    fun exec() {
        println(ext.sdkDir.get())
    }
}
