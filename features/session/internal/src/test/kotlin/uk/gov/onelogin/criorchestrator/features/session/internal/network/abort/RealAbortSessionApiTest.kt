package uk.gov.onelogin.criorchestrator.features.session.internal.network.abort

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import uk.gov.android.network.api.v2.ApiResponse
import uk.gov.onelogin.criorchestrator.features.config.internalapi.FakeConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey
import uk.gov.onelogin.criorchestrator.libraries.testing.networking.Imposter
import uk.gov.onelogin.criorchestrator.libraries.testing.networking.createTestHttpClient
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class RealAbortSessionApiTest {
    private val fakeConfigStore = FakeConfigStore()

    private lateinit var api: AbortSessionApi

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
        api =
            RealAbortSessionApi(
                networkService = createTestHttpClient(),
                configStore = fakeConfigStore,
            )
    }

    @Test
    fun `it responds`() =
        runTest {
            val result = api.abortSession("sessionId")
            val success = result as ApiResponse.Success
            assertEquals("example", success.response)
        }
}
