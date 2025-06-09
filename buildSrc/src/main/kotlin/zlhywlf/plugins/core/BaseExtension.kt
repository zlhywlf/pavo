package zlhywlf.plugins.core

import org.gradle.api.Project
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.property
import zlhywlf.models.Platform
import zlhywlf.utils.ExtensionUtil
import zlhywlf.utils.PlatformUtil

abstract class BaseExtension protected constructor(project: Project) {
    private val cacheDir = project.layout.projectDirectory.dir(ExtensionUtil.GRADLE_CACHE_DIR_NAME)
    internal val platform = project.objects.property<Platform>()
    internal val sdkDir: DirectoryProperty = project.objects.directoryProperty()
    internal val workDir: DirectoryProperty by lazy {
        project.objects.directoryProperty().convention(cacheDir.dir(workDirName))
    }

    val version: Property<String> by lazy {
        project.objects.property<String>().convention(defVersion)
    }
    val distUrl: Property<String> by lazy {
        project.objects.property<String>().convention(defDistUrl)
    }

    init {
        val platform = PlatformUtil.parsePlatform(project)
        this.platform.set(platform)
    }

    internal abstract val workDirName: String
    internal abstract val defVersion: String
    internal abstract val defDistUrl: String
}
