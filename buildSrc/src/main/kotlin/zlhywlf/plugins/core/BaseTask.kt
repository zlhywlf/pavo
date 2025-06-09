package zlhywlf.plugins.core

import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.Internal

abstract class BaseTask<E : BaseExtension> : DefaultTask() {
    @get:Internal
    protected val gradleHelper: GradleHelper = project.objects.newInstance(GradleHelper::class.java)

    @get:InputFile
    protected val archiveFile: RegularFileProperty = project.objects.fileProperty()

    @get:Internal
    protected val ext: E by lazy {
        getExtension()
    }

    @Internal
    internal abstract fun getExtension(): E
}
