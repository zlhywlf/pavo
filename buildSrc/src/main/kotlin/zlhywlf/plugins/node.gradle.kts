package zlhywlf.plugins

import zlhywlf.plugins.node.NodeExtension
import zlhywlf.plugins.node.configureNodeExtension
import zlhywlf.plugins.node.addTasks
import zlhywlf.utils.ExtensionUtil


val nodeExt = ExtensionUtil.getOrCreate<NodeExtension>(project)
configureNodeExtension(project, nodeExt)
addTasks(project)
