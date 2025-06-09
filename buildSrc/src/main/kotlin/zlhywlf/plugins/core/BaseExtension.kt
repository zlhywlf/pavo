package zlhywlf.plugins.core

import org.gradle.api.Project
import zlhywlf.utils.ExtensionUtil

open class BaseExtension protected constructor(project: Project) {
    protected val cacheDir = project.layout.projectDirectory.dir(ExtensionUtil.GRADLE_CACHE_DIR_NAME)
}
