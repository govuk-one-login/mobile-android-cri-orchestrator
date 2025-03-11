package uk.gov.onelogin.criorchestrator.features.session.internal.network

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import uk.gov.android.network.api.ApiResponse
import uk.gov.android.network.client.StubHttpClient
import uk.gov.logging.testdouble.SystemLogger
import uk.gov.onelogin.criorchestrator.features.config.publicapi.InMemoryConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.StubConfig

class SessionApiImplTest {
    private val logger = SystemLogger()
    private val configStore = InMemoryConfigStore(logger)
    private val sessionApiImpl =
        SessionApiImpl(
            StubHttpClient(
                ApiResponse.Success<String>(
                    """
                    {
                        "sessionId": "test session ID",
                        "redirectUri": "https://example/redirect",
                        "state": "11112222333344445555666677778888"
                    }
                    """.trimIndent(),
                ),
            ),
            configStore = configStore,
            logger = logger,
        )

    @Test
    fun `session API implementation returns stubbed API response`() {
        configStore.write(StubConfig.provideConfig())
        runTest {
            val expected =
                ApiResponse.Success<String>(
                    """
                    {
                        "sessionId": "test session ID",
                        "redirectUri": "https://example/redirect",
                        "state": "11112222333344445555666677778888"
                    }
                    """.trimIndent(),
                )

            val result = sessionApiImpl.getActiveSession()
            assertEquals(result, expected)
        }
    }
}
