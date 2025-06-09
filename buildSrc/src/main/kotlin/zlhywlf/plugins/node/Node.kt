package zlhywlf.plugins.node

import org.gradle.api.Project
import zlhywlf.plugins.core.BaseExtension
import zlhywlf.utils.ProviderUtil

class NodeExtension(project: Project) : BaseExtension(project) {

    override val defVersion: String = DEFAULT_NODE_VERSION
    override val workDirName: String = DEFAULT_WORK_DIR_NAME
    override val defDistUrl: String = DEFAULT_NODE_DIST_URL

    init {
        this.sdkDir.set(ProviderUtil.zip(this.platform, this.workDir, this.version).map { (p, w, v) ->
            w.dir("node-v$v-${p.osName}-${p.osArch}")
        })
        this.sdkDir.finalizeValueOnRead()
    }

    companion object {
        private const val DEFAULT_NODE_VERSION = "24.0.1"
        private const val DEFAULT_NODE_DIST_URL = "https://nodejs.org/dist"
        private const val DEFAULT_WORK_DIR_NAME = "nodejs"
    }
}

internal fun addTasks(project: Project) {

}
