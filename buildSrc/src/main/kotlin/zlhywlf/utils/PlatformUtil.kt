package zlhywlf.utils

import org.gradle.api.Project
import zlhywlf.models.Platform

object PlatformUtil {
    internal fun parsePlatform(project: Project): Platform {
        return Platform(
            "win",
            "x64"
        )
    }
}
