package uk.gov.onelogin.criorchestrator.features.session.internal.usecases

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import org.junit.jupiter.api.assertNull
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.mock
import org.mockito.kotlin.given
import uk.gov.android.network.api.ApiResponse
import uk.gov.logging.testdouble.SystemLogger
import uk.gov.onelogin.criorchestrator.features.session.internal.network.abort.AbortSessionApi
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.AbortSession
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.FakeSessionStore
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.createTestInstance
import kotlin.test.assertTrue

class AbortSessionImplTest {
    private val session = Session.Companion.createTestInstance()
    private val sessionId = session.sessionId
    private val sessionStore =
        FakeSessionStore(
            session = session,
        )
    private val abortSessionApi = mock<AbortSessionApi>()
    private val logger = SystemLogger()

    private val abortSession =
        AbortSessionImpl(
            sessionStore = sessionStore,
            abortSessionApi = abortSessionApi,
            logger = logger,
        )

    @Test
    fun `given session is null, it logs an error`() =
        runTest {
            sessionStore.write(null)

            abortSession()

            assertTrue(logger.contains("Tried to abort a non-existent session"))
        }

    @Test
    fun `given session is null, it returns success`() =
        runTest {
            sessionStore.write(null)

            val result = abortSession()

            assertEquals(AbortSession.Result.Success, result)
        }

    @Test
    fun `given api response is success, it clears the session store`() =
        runTest {
            givenResponse(ApiResponse.Success(""))
            assertNotNull(sessionStore.read().first())

            abortSession()

            assertNull(sessionStore.read().first())
        }

    @Test
    fun `given api response is success, it returns success`() =
        runTest {
            givenResponse(ApiResponse.Success(""))

            val result = abortSession()

            assertEquals(AbortSession.Result.Success, result)
        }

    @Test
    fun `given api response is unrecoverable error, it clears the session store`() =
        runTest {
            givenResponse(ApiResponse.Failure(401, error = Exception("error")))
            assertNotNull(sessionStore.read().first())

            abortSession()

            assertNull(sessionStore.read().first())
        }

    @Test
    fun `given api response is unrecoverable error, it returns error`() =
        runTest {
            val exception = Exception("error")
            givenResponse(ApiResponse.Failure(401, error = exception))

            val result = abortSession()

            assertEquals(AbortSession.Result.Error.Unrecoverable(exception), result)
        }

    @Test
    fun `given api response is offline, it doesn't clear the session store`() =
        runTest {
            givenResponse(ApiResponse.Offline)
            assertNotNull(sessionStore.read().first())

            abortSession()

            assertNotNull(sessionStore.read().first())
        }

    @Test
    fun `given api response is offline, it returns error`() =
        runTest {
            givenResponse(ApiResponse.Offline)

            val result = abortSession()

            assertEquals(AbortSession.Result.Error.Offline, result)
        }

    @Test
    fun `given api response is loading, it throws`() =
        runTest {
            givenResponse(ApiResponse.Loading)

            assertThrows<IllegalStateException> {
                abortSession()
            }
        }

    private suspend fun givenResponse(apiResponse: ApiResponse) =
        given(abortSessionApi.abortSession(sessionId)).willReturn(apiResponse)
}
