package zlhywlf.plugins.python

import zlhywlf.plugins.core.SetupTask
import zlhywlf.utils.ExtensionUtil

abstract class PythonSetupTask : SetupTask<PythonExtension>(
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

    override fun exec() {
        super.exec()
    }

    override fun initExtension(): PythonExtension {
        return ExtensionUtil.getOrCreate<PythonExtension>(project)
    }
}
