package uk.gov.onelogin.criorchestrator.sdk.internal

import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import uk.gov.onelogin.criorchestrator.features.config.internal.ConfigComponent
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.publicapi.nfc.NfcConfigKey

class CriOrchestratorSingletonImplTest {
    @Test
    fun `it applies the default config`() =
        runTest {
            val customEntry =
                Config.Entry(
                    key = SdkConfigKey.IdCheckAsyncBackendBaseUrl,
                    Config.Value.StringValue("my custom base url"),
                )
            val singleton =
                CriOrchestratorSingletonImpl(
                    authenticatedHttpClient = mock(),
                    analyticsLogger = mock(),
                    userConfig =
                        Config(
                            entries =
                                persistentListOf(
                                    customEntry,
                                ),
                        ),
                    logger = mock(),
                    applicationContext = mock(),
                )

            val config =
                (singleton.component as ConfigComponent)
                    .configStore()
                    .readAll()
                    .first()

            assertEquals(
                config,
                Config(
                    entries =
                        persistentListOf(
                            Config.Entry<Config.Value.BooleanValue>(
                                key = SdkConfigKey.BypassIdCheckAsyncBackend,
                                Config.Value.BooleanValue(false),
                            ),
                            Config.Entry<Config.Value.StringValue>(
                                key = SdkConfigKey.BypassJourneyType,
                                Config.Value.StringValue(SdkConfigKey.BypassJourneyType.OPTION_MOBILE_APP_MOBILE),
                            ),
                            Config.Entry<Config.Value.StringValue>(
                                key = SdkConfigKey.BypassAbortSessionApiCall,
                                Config.Value.StringValue(SdkConfigKey.BypassAbortSessionApiCall.OPTION_SUCCESS),
                            ),
                            Config.Entry<Config.Value.BooleanValue>(
                                key = SdkConfigKey.DebugAppReviewPrompts,
                                Config.Value.BooleanValue(false),
                            ),
                            Config.Entry<Config.Value.BooleanValue>(
                                key = IdCheckWrapperConfigKey.EnableManualLauncher,
                                Config.Value.BooleanValue(false),
                            ),
                            Config.Entry<Config.Value.StringValue>(
                                key = NfcConfigKey.NfcAvailability,
                                Config.Value.StringValue(NfcConfigKey.NfcAvailability.OPTION_DEVICE),
                            ),
                            customEntry,
                        ),
                ),
            )
        }
}
