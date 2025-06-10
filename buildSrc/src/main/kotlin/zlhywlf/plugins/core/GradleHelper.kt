package zlhywlf.plugins.core

import org.gradle.api.Action
import org.gradle.api.file.*
import org.gradle.api.tasks.WorkResult
import java.io.File
import javax.inject.Inject

open class GradleHelper @Inject constructor(
    private val fileSystemOperations: FileSystemOperations,
    private val archiveOperations: ArchiveOperations
) {
    internal fun delete(action: Action<DeleteSpec>): WorkResult {
        return fileSystemOperations.delete(action)
    }

    internal fun copy(action: Action<CopySpec>): WorkResult {
        return fileSystemOperations.copy(action)
    }

    fun zipTree(tarPath: File): FileTree {
        return archiveOperations.zipTree(tarPath)
    }
}
