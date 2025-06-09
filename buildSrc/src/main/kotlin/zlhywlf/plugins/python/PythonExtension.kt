package zlhywlf.plugins.python

import org.gradle.api.Project
import zlhywlf.plugins.core.BaseExtension
import zlhywlf.utils.ProviderUtil

class PythonExtension(project: Project) : BaseExtension(project) {

    override val defVersion: String = "3.12.10"
    override val workDirName: String = "python"
    override val defDistUrl: String = "https://www.python.org/ftp/python"

    init {
        this.sdkDir.set(ProviderUtil.zip(this.workDir, this.version).map { (w, v) ->
            w.dir("python-$v")
        })
        this.sdkDir.finalizeValueOnRead()
    }
}
