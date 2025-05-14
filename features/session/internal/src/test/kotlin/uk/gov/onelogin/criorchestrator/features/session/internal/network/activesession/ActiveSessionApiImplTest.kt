package uk.gov.onelogin.criorchestrator.features.session.internal.network.activesession

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import uk.gov.android.network.api.ApiResponse
import uk.gov.onelogin.criorchestrator.features.config.internalapi.FakeConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey
import uk.gov.onelogin.criorchestrator.libraries.testing.networking.Imposter
import uk.gov.onelogin.criorchestrator.libraries.testing.networking.createTestHttpClient

@OptIn(ExperimentalCoroutinesApi::class)
class ActiveSessionApiImplTest {
    private val fakeConfigStore = FakeConfigStore()

    private lateinit var activeSessionApiImpl: ActiveSessionApi

    @BeforeEach
    fun setup() {
        val imposter = Imposter.createMockEngine()
        fakeConfigStore.write(
            Config.Entry(
                key = SdkConfigKey.IdCheckAsyncBackendBaseUrl,
                value =
                    Config.Value.StringValue(
                        imposter.baseUrl.toString(),
                    ),
            ),
        )
        activeSessionApiImpl =
            ActiveSessionApiImpl(
                httpClient = createTestHttpClient(),
                configStore = fakeConfigStore,
            )
    }

    @Test
    fun `session API implementation returns stubbed API response`() =
        runTest {
            val expected =
                ApiResponse.Success<String>(
                    "{\"sessionId\":\"37aae92b-a51e-4f68-b571-8e455fb0ec34\"," +
                        "\"redirectUri\":\"https://example/redirect\"," +
                        "\"state\":\"11112222333344445555666677778888\"}",
                )

            val result = activeSessionApiImpl.getActiveSession()
            Assertions.assertEquals(expected, result)
        }
}
