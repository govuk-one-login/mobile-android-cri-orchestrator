package uk.gov.onelogin.criorchestrator.sdk.internal.config

import kotlinx.collections.immutable.toPersistentList
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey
import java.util.stream.Stream

class DefaultConfigTest {
    companion object {
        private val idCheckAsyncBackendBaseUrlEntry =
            Config.Entry<Config.Value.StringValue>(
                key = SdkConfigKey.IdCheckAsyncBackendBaseUrl,
                value = Config.Value.StringValue("baseurl"),
            )
        private val requiredUserConfig =
            listOf(
                idCheckAsyncBackendBaseUrlEntry,
            )

        @JvmStatic
        fun missingEntry(): Stream<Arguments> =
            Stream.of(
                Arguments.of(
                    idCheckAsyncBackendBaseUrlEntry,
                ),
            )
    }

    @ParameterizedTest
    @MethodSource("missingEntry")
    fun `given required config isn't provided, it throws`(missingEntry: Config.Entry<*>) {
        val userConfig =
            (requiredUserConfig - missingEntry)
                .toPersistentList()
                .let { Config(it) }

        assertThrows<IllegalArgumentException> {
            Config.fromUserConfig(userConfig = userConfig)
        }
    }
}
