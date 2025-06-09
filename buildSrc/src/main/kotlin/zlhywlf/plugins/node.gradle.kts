package zlhywlf.plugins

import zlhywlf.plugins.node.NodeExtension
import zlhywlf.utils.ExtensionUtil
import zlhywlf.plugins.node.NodeSetupTask

val ext = ExtensionUtil.getOrCreate<NodeExtension>(project)
project.tasks.register(NodeSetupTask.NAME, NodeSetupTask::class.java)
