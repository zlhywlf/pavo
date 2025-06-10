package zlhywlf.plugins.node

import zlhywlf.plugins.core.SetupTask
import zlhywlf.utils.ExtensionUtil

abstract class NodeSetupTask : SetupTask<NodeExtension>(
    lambda@{ p -> "${p.osName}-${p.osArch}" },
    "org.nodejs",
    "node",
    "v[revision]/[artifact](-v[revision]-[classifier]).[ext]",
    "zip" to "tar.gz",
) {
    init {
        group = "node"
        description = "Setup nodejs"
    }

    companion object {
        const val NAME = "nodeSetup"
    }

    override fun exec() {
        super.exec()
        val af = archiveFile.get().asFile
        val nodeDir = ext.sdkDir
        val nodeBinDir = if (ext.platform.get().isWindows()) nodeDir else nodeDir.map { it.dir("bin") }
        val archivePath = nodeBinDir.map { it.dir("../") }
        if (af.name.endsWith("zip")) {
            gradleHelper.copy {
                from(gradleHelper.zipTree(af))
                into(archivePath)
            }
        }
    }

    override fun initExtension(): NodeExtension {
        return ExtensionUtil.getOrCreate<NodeExtension>(project)
    }
}
