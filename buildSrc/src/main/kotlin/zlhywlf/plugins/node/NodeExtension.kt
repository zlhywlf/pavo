package zlhywlf.plugins.node

import org.gradle.api.Project
import zlhywlf.plugins.core.BaseExtension
import zlhywlf.utils.ProviderUtil

class NodeExtension(project: Project) : BaseExtension(project) {

    override val defVersion: String = "24.0.1"
    override val workDirName: String = "nodejs"
    override val defDistUrl: String = "https://nodejs.org/dist"

    init {
        this.sdkDir.set(ProviderUtil.zip(this.platform, this.workDir, this.version).map { (p, w, v) ->
            w.dir("node-v$v-${p.osName}-${p.osArch}")
        })
        this.sdkDir.finalizeValueOnRead()
    }
}
