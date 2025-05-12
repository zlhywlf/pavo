package zlhywlf.utils

import com.github.javafaker.Faker
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class PlatformTest {

    @ParameterizedTest
    @MethodSource("platformData")
    internal fun parseOsTypeTest(key: String, ex: OsType) {
        val len = 5
        val pre = faker.lorem().fixedString(len)
        val post = faker.lorem().fixedString(len)
        assert(parseOsType("$pre$key$post") == ex)
    }

    companion object {
        lateinit var faker: Faker

        @BeforeAll
        @JvmStatic
        fun setupFaker() {
            faker = Faker()
        }

        @JvmStatic
        fun platformData(): Stream<Arguments> {
            return Stream.of(
                Arguments.of("windows", OsType.WINDOWS),
                Arguments.of("mac", OsType.MAC),
                Arguments.of("linux", OsType.LINUX),
            )
        }
    }

}
