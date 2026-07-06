package uk.gov.onelogin.criorchestrator.features.session.internal.network.activesession

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import uk.gov.android.network.api.v2.ApiResponse
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
            val result = activeSessionApi.getActiveSession()
            assertEquals(200, (result as ApiResponse.Success).status)
            assertEquals(
                """
                    {
                        "sessionId": "test-session-id",
                        "redirectUri": "https://example/redirect",
                        "state": "11112222333344445555666677778888"
                    }
                    """.replace("\\s".toRegex(), ""),
                result.response,
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
            val result = activeSessionApi.getActiveSession()
            assertEquals(200, (result as ApiResponse.Success).status)
            assertEquals(
                """
                    {
                        "sessionId": "test-session-id",
                        "state": "11112222333344445555666677778888"
                    }
                    """.replace("\\s".toRegex(), ""),
                result.response,
            )
        }
}
