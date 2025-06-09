package zlhywlf.plugins.core

import org.gradle.api.Action
import org.gradle.api.file.DeleteSpec
import org.gradle.api.file.FileSystemOperations
import org.gradle.api.tasks.WorkResult
import javax.inject.Inject

open class GradleHelper @Inject constructor(
    private val fileSystemOperations: FileSystemOperations
) {
    internal fun delete(action: Action<DeleteSpec>): WorkResult {
        return fileSystemOperations.delete(action)
    }
}
