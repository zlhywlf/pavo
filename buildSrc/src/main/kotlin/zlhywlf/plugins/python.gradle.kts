package zlhywlf.plugins

import zlhywlf.plugins.python.PythonSetupTask
import zlhywlf.plugins.python.PythonExtension
import zlhywlf.utils.ExtensionUtil

val ext = ExtensionUtil.getOrCreate<PythonExtension>(project)
project.tasks.register(PythonSetupTask.NAME, PythonSetupTask::class.java)
