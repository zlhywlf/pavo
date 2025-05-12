package zlhywlf.plugins

import zlhywlf.plugins.node.NodeExtension
import zlhywlf.plugins.node.configureNodeExtension


val nodeExtension: NodeExtension = NodeExtension.create(project)
configureNodeExtension(project, nodeExtension)
