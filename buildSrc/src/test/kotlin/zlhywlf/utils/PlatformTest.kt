package zlhywlf.utils

import org.junit.jupiter.api.Test

class PlatformTest {

    @Test
    internal fun parseOsTypeTest() {
        assert(parseOsType("windows") == OsType.WINDOWS)
    }

}
