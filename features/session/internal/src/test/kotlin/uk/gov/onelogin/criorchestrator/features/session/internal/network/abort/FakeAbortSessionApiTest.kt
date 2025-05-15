package uk.gov.onelogin.criorchestrator.features.session.internal.network.abort

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import uk.gov.android.network.api.ApiResponse

class FakeAbortSessionApiTest {
    @Test
    fun `it returns success`() =
        runTest {
            val fakeApi = FakeAbortSessionApi()
            val result = fakeApi.abortSession("sessionId")
            assertEquals(ApiResponse.Success(""), result)
        }
}
