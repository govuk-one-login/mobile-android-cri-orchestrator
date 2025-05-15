package uk.gov.onelogin.criorchestrator.features.session.internal.network.activesession

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import uk.gov.android.network.api.ApiResponse
import uk.gov.onelogin.criorchestrator.features.config.internalapi.FakeConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey

class FakeActiveSessionApiTest {
    private val configStore = FakeConfigStore()
    private val activeSessionApi =
        FakeActiveSessionApi(
            configStore = configStore,
        )

    @Test
    fun `given journey type should be MAM, it returns correct response`() =
        runTest {
            configStore.write(
                entry =
                    Config.Entry<Config.Value.StringValue>(
                        key = SdkConfigKey.BypassJourneyType,
                        Config.Value.StringValue(SdkConfigKey.BypassJourneyType.OPTION_MOBILE_APP_MOBILE),
                    ),
            )
            assertEquals(
                ApiResponse.Success<String>(
                    """
                    {
                        "sessionId": "test session ID",
                        "redirectUri": "https://example/redirect",
                        "state": "11112222333344445555666677778888"
                    }
                    """.trimIndent(),
                ),
                activeSessionApi.getActiveSession(),
            )
        }

    @Test
    fun `given journey type should be DAD, it returns correct response`() =
        runTest {
            configStore.write(
                entry =
                    Config.Entry<Config.Value.StringValue>(
                        key = SdkConfigKey.BypassJourneyType,
                        Config.Value.StringValue(SdkConfigKey.BypassJourneyType.OPTION_DESKTOP_APP_DESKTOP),
                    ),
            )
            assertEquals(
                ApiResponse.Success<String>(
                    """
                    {
                        "sessionId": "test session ID",
                        "redirectUri": null,
                        "state": "11112222333344445555666677778888"
                    }
                    """.trimIndent(),
                ),
                activeSessionApi.getActiveSession(),
            )
        }
}
