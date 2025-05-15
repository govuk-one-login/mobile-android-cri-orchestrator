package uk.gov.onelogin.criorchestrator.features.session.internal.network.activesession

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import uk.gov.android.network.api.ApiResponse

class FakeActiveSessionApiTest {
    @Test
    fun `verify fake session API returns intended response`() =
        runTest {
            Assertions.assertEquals(
                ApiResponse.Success<String>(
                    """
                    {
                        "sessionId": "test session ID",
                        "redirectUri": "https://example/redirect",
                        "state": "11112222333344445555666677778888"
                    }
                    """.trimIndent(),
                ),
                FakeActiveSessionApi().getActiveSession(),
            )
        }
}
