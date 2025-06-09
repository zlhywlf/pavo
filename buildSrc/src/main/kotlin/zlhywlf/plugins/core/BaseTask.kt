package zlhywlf.plugins.core

import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.Internal

abstract class BaseTask : DefaultTask() {
    @get:Internal
    protected val gradleHelper: GradleHelper = project.objects.newInstance(GradleHelper::class.java)
    @get:InputFile
    protected val archiveFile: RegularFileProperty = project.objects.fileProperty()
}
