package zlhywlf.utils

import java.util.*
import java.util.concurrent.Callable
import org.gradle.api.Project

data class Platform(val osName: String, val osArch: String) {
    fun isWindows(): Boolean {
        return osName == OsType.WINDOWS.osName
    }
}

internal enum class OsType(val osName: String) {
    WINDOWS("win"),
    MAC("darwin"),
    LINUX("linux"),
    FREEBSD("linux"),
    SUN("sunos"),
    AIX("aix"),
}

internal fun parseOsType(type: String): OsType {
    val name = type.lowercase(Locale.getDefault())
    return when {
        name.contains("windows") -> OsType.WINDOWS
        name.contains("mac") -> OsType.MAC
        name.contains("linux") -> OsType.LINUX
        name.contains("freebsd") -> OsType.FREEBSD
        name.contains("sunos") -> OsType.SUN
        name.contains("aix") -> OsType.AIX
        else -> error("Unsupported OS: $name")
    }
}

internal fun parseOsArch(osType: OsType, arch: String, uname: Callable<String>): String {
    if (osType == OsType.WINDOWS) {
        return when {
            arch.startsWith("aarch") || arch.startsWith("arm")
            -> {
                val wmiArch = uname.call()
                return when (wmiArch) {
                    // https://learn.microsoft.com/en-us/windows/win32/api/sysinfoapi/ns-sysinfoapi-system_info#members
                    "9" -> "x64"
                    "5" -> "arm"
                    "12" -> "arm64"
                    "6" -> "IA64"
                    "0" -> "x86"
                    "0xffff" -> "Unknown"
                    else -> error("Unexpected Win32_Processor.Architecture: $arch")
                }
            }

            arch.contains("64") -> "x64"
            else -> "x86"
        }
    }
    return when {
        arch == "arm" || arch.startsWith("aarch") -> uname.call()
            .mapIf({ it == "armv8l" || it == "aarch64" }) { "arm64" }
            .mapIf({ it == "x86_64" }) { "x64" }

        arch == "ppc64" -> "ppc64"
        arch == "ppc64le" -> "ppc64le"
        arch == "s390x" -> "s390x"
        arch.contains("64") -> "x64"
        else -> "x86"
    }
}

internal fun parsePlatform(uname: () -> String): Platform {
    val osType = parseOsType(System.getProperty("os.name"))
    val arch = System.getProperty("os.arch").lowercase(Locale.getDefault())
    return Platform(
        osType.osName,
        parseOsArch(osType, arch, uname)
    )
}

fun parsePlatform(project: Project): Platform {
    return parsePlatform {
        val cmd = project.providers.exec {
            val osType = parseOsType(System.getProperty("os.name"))
            if (osType == OsType.WINDOWS) {
                this.executable = "powershell"
                this.args = listOf("-NoProfile", "-Command", "(Get-WmiObject Win32_Processor).Architecture")
            } else {
                this.executable = "uname"
                this.args = listOf("-m")
            }
        }
        cmd.standardOutput.asText.get().trim()
    }
}
