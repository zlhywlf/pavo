package zlhywlf.models

internal data class Platform(val osName: String, val osArch: String) {
    fun isWindows(): Boolean {
        return osName == OsEnum.WINDOWS.osName
    }
}
