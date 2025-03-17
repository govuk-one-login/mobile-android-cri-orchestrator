package uk.gov.onelogin.criorchestrator.features.session.internal

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import uk.gov.android.network.api.ApiResponse
import uk.gov.onelogin.criorchestrator.features.session.internal.network.FakeSessionApi

class FakeSessionApiTest {
    @Test
    fun `verify fake session API returns intended response`() =
        runTest {
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
                FakeSessionApi().getActiveSession()
            )
        }
}