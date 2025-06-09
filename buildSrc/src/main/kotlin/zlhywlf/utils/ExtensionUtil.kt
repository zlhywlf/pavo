package zlhywlf.utils

import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionContainer
import java.util.*

object ExtensionUtil {
    const val GRADLE_CACHE_DIR_NAME = ".gradle"
    inline fun <reified T : Any> getOrCreate(project: Project): T {
        val name = T::class.simpleName!!.replaceFirstChar { it.lowercase(Locale.getDefault()) }
        val container: ExtensionContainer = project.extensions
        return container.findByType(T::class.java) ?: run {
            val instance = T::class.java.getConstructor(Project::class.java).newInstance(project)
            container.add(name, instance)
            instance
        }
    }
}
