package zlhywlf.plugins

import zlhywlf.plugins.node.NodeExtension
import zlhywlf.plugins.node.configureNodeSetupTask
import zlhywlf.plugins.node.configureNodeExtension
import zlhywlf.plugins.node.addTasks


val nodeExtension: NodeExtension = NodeExtension.create(project)
configureNodeExtension(project, nodeExtension)
addTasks(project)
afterEvaluate {
    configureNodeSetupTask(project, nodeExtension)
}
