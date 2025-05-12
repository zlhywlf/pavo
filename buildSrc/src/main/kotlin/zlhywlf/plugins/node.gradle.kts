package zlhywlf.plugins

import zlhywlf.node.NodeExtension
import zlhywlf.node.configureNodeExtension


val groupName = "jupiter.node"
val nodeExtension: NodeExtension = project.extensions.create<NodeExtension>(NodeExtension.NAME, project)

tasks.register("build") {
    group = groupName
    configureNodeExtension(project, nodeExtension)
}
