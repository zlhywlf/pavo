package zlhywlf.plugins

import zlhywlf.utils.parseOsType

open class NodeExtension(project: Project) {
    val cacheDir = project.layout.projectDirectory.dir(".gradle")
}

val groupName = "jupiter.node"
val nodeExtension: NodeExtension = project.extensions.create<NodeExtension>("nodePlugin", project)

private fun npm() {
    println(System.getProperty("os.name"))
    println(parseOsType(System.getProperty("os.name")))
}

tasks.register("build") {
    group = groupName
    npm()
    doLast {
        println("node: ${nodeExtension.cacheDir} ")
    }
}
