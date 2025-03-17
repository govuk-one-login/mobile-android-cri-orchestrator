package uk.gov.onelogin.criorchestrator.features.config.publicapi

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class SdkConfigKeyTest {

    @ParameterizedTest(name = "{0}")
    @MethodSource("assertCorrectKeyNames")
    fun `verify correct name for SDK config key`(
        expectedName: String,
        actualName: String,
    ) {
        assertEquals(
            expectedName,
            actualName,
        )
    }

    companion object {
        @JvmStatic
        @Suppress("LongMethod")
        fun assertCorrectKeyNames(): Stream<Arguments> =
            Stream.of(
                arguments(
                    "ID Check async backend base URL",
                    SdkConfigKey.IdCheckAsyncBackendBaseUrl.name,
                ),
                arguments(
                    "Use fake ID Check async backend",
                    SdkConfigKey.FakeIdCheckAsyncBackend.name,
                ),
            )
    }
}
