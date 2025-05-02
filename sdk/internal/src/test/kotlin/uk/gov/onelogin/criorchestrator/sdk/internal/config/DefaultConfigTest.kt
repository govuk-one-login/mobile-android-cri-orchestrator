package uk.gov.onelogin.criorchestrator.sdk.internal.config

import kotlinx.collections.immutable.toPersistentList
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.publicapi.nfc.NfcConfigKey
import java.util.stream.Stream
import kotlin.test.assertContains
import kotlin.test.assertEquals

class DefaultConfigTest {
    companion object {
        private val idCheckAsyncBackendBaseUrlEntry =
            Config.Entry<Config.Value.StringValue>(
                key = SdkConfigKey.IdCheckAsyncBackendBaseUrl,
                value = Config.Value.StringValue("baseurl"),
            )
        private val bypassIdCheckAsyncBackendEntry =
            Config.Entry<Config.Value.BooleanValue>(
                key = SdkConfigKey.BypassIdCheckAsyncBackend,
                Config.Value.BooleanValue(false),
            )
        private val nfcAvailabilityEntry =
            Config.Entry<Config.Value.StringValue>(
                key = NfcConfigKey.NfcAvailability,
                Config.Value.StringValue(NfcConfigKey.NfcAvailability.OPTION_DEVICE),
            )
        private val requiredUserConfig =
            listOf(
                idCheckAsyncBackendBaseUrlEntry,
                bypassIdCheckAsyncBackendEntry,
                nfcAvailabilityEntry,
            )

        @JvmStatic
        fun missingEntry(): Stream<Arguments> =
            Stream.of(
                Arguments.of(
                    idCheckAsyncBackendBaseUrlEntry,
                    IllegalArgumentException("IdCheckAsyncBackendBaseUrl config must be provided"),
                ),
                Arguments.of(
                    bypassIdCheckAsyncBackendEntry,
                    null,
                ),
                Arguments.of(
                    nfcAvailabilityEntry,
                    null,
                ),
            )
    }

    @ParameterizedTest
    @MethodSource("missingEntry")
    fun `given config is missing, it either throws or is provided by default`(
        missingEntry: Config.Entry<*>,
        expectedException: IllegalArgumentException?,
    ) {
        val userConfig =
            (requiredUserConfig - missingEntry)
                .toPersistentList()
                .let { Config(it) }

        if (expectedException != null) {
            // Config is required
            val exception =
                assertThrows<IllegalArgumentException> {
                    Config.fromUserConfig(userConfig = userConfig)
                }

            assertEquals(
                expectedException.message,
                exception.message,
            )
        } else {
            // Config is already provided by default
            val config = Config.fromUserConfig(userConfig = userConfig)

            assertContains(
                config.keys,
                missingEntry.key,
            )
        }
    }
}
