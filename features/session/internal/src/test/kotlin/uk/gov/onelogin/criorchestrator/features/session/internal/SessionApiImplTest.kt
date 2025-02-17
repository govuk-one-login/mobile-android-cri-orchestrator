package uk.gov.onelogin.criorchestrator.features.session.internal

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import uk.gov.android.network.api.ApiResponse
import uk.gov.android.network.client.StubHttpClient
import uk.gov.onelogin.criorchestrator.libraries.androidutils.resources.FakeResourceProvider

class SessionApiImplTest {
    private val fakeResourceProvider = FakeResourceProvider()
    private val sessionApiImpl =
        SessionApiImpl(
            fakeResourceProvider,
            StubHttpClient(
                ApiResponse.Success<String>("Test"),
            ),
        )

    @Test
    fun `session API implementation returns stubbed API response`() {
        val expected = ApiResponse.Success("Test")
        val result =
            runBlocking {
                return@runBlocking sessionApiImpl.getActiveSession()
            }
        assertEquals(result, expected)
    }
}
