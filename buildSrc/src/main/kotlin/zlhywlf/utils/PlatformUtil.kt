package zlhywlf.utils

import java.util.*

internal enum class OsType(val osName: String) {
    WINDOWS("win"),
    MAC("darwin"),
    LINUX("linux"),
}

internal fun parseOsType(type: String): OsType {
    val name = type.lowercase(Locale.getDefault())
    return when {
        name.contains("windows") -> OsType.WINDOWS
        name.contains("mac") -> OsType.MAC
        name.contains("linux") -> OsType.LINUX
        else -> error("Unsupported OS: $name")
    }
}
