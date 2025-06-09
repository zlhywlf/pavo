package zlhywlf.plugins.python

import org.gradle.api.tasks.Internal
import zlhywlf.plugins.core.BaseTask
import org.gradle.api.tasks.TaskAction
import zlhywlf.utils.ExtensionUtil

abstract class PythonSetupTask : BaseTask<PythonExtension>(
    lambda@{ _ -> "" },
    "org.python",
    "python",
    "[revision]/[artifact](-[revision]).[ext]",
    "exe" to "tgz",
) {

    init {
        group = "python"
        description = "Setup python"
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

    override fun getExtension(): PythonExtension {
        return ExtensionUtil.getOrCreate<PythonExtension>(project)
    }
}
