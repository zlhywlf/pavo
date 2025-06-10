package zlhywlf.plugins.core

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction

abstract class BaseTask<E : BaseExtension> : DefaultTask() {
    @get:Internal
    protected val gradleHelper: GradleHelper = project.objects.newInstance(GradleHelper::class.java)

    @get:Internal
    protected val ext: E by lazy {
        initExtension()
    }

    @TaskAction
    abstract fun exec()

    internal abstract fun initExtension(): E
}
