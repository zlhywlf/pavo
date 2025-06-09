package zlhywlf.plugins.python

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import zlhywlf.utils.ExtensionUtil

abstract class PythonSetupTask : DefaultTask() {
    private val ext = ExtensionUtil.getOrCreate<PythonExtension>(project)

    init {
        group = "python"
        description = "Setup python"
    }

    companion object {
        const val NAME = "pythonSetup"
    }

    @TaskAction
    fun exec() {
        println(ext.sdkDir.get())
    }
}
