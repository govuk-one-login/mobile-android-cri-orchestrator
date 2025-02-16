package uk.gov.onelogin.criorchestrator.features.session.internal

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import uk.gov.android.network.api.ApiResponse
import uk.gov.android.network.client.StubHttpClient

@RunWith(AndroidJUnit4::class)
class SessionApiImplTest {
    private val context: Context = ApplicationProvider.getApplicationContext()
    private val fakeSessionApiImpl =
        SessionApiImpl(
            context,
            StubHttpClient(
                ApiResponse.Success<String>("Test"),
            ),
        )

    @Test
    fun `session API implementation returns stubbed API response`() {
        val expected = ApiResponse.Success("Test")
        val result =
            runBlocking {
                return@runBlocking fakeSessionApiImpl.getActiveSession()
            }
        assertEquals(result, expected)
    }
}
