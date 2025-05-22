package uk.gov.onelogin.criorchestrator.features.session.internal.usecases

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNull
import uk.gov.onelogin.criorchestrator.features.session.internal.SessionReader
import uk.gov.onelogin.criorchestrator.features.session.internal.StubSessionReader
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.FakeSessionStore
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.createTestInstance

class RefreshActiveSessionImplTest {
    private val session = Session.createTestInstance()
    private val sessionReader =
        StubSessionReader(
            result = SessionReader.Result.IsActive(session),
        )
    private val sessionStore = FakeSessionStore(session = null)
    private val refreshActiveSession =
        RefreshActiveSessionImpl(
            sessionReader = sessionReader,
            sessionStore = sessionStore,
        )

    @Test
    fun `given active session, when called, it stores the session`() =
        runTest {
            sessionReader.result = SessionReader.Result.IsActive(session)

            refreshActiveSession()

            val storedSession = sessionStore.read().value

            assertEquals(storedSession, session)
        }

    @Test
    fun `given session changed, when called, it stores the session`() =
        runTest {
            val firstSession =
                Session.createTestInstance(
                    sessionId = "first-test-session",
                )
            val secondSession =
                Session.createTestInstance(
                    sessionId = "second-test-session",
                )

            sessionReader.result = SessionReader.Result.IsActive(firstSession)
            refreshActiveSession()

            sessionReader.result = SessionReader.Result.IsActive(secondSession)
            refreshActiveSession()

            val storedSession = sessionStore.read().value

            assertEquals(secondSession, storedSession)
        }

    @Test
    fun `given session became inactive, when called, it updates the session`() =
        runTest {
            assertEquals(session.sessionState, Session.State.Created)
            sessionReader.result = SessionReader.Result.IsActive(session)
            refreshActiveSession()

            sessionReader.result = SessionReader.Result.IsNotActive
            refreshActiveSession()

            val storedSession = sessionStore.read().value

            assertEquals(storedSession?.sessionId, storedSession?.sessionId)
            assertNotEquals(storedSession?.sessionState, Session.State.Created)
        }

    @Test
    fun `given cached active session but cannot determine new state, when called, existing session is not updated`() =
        runTest {
            sessionReader.result = SessionReader.Result.IsActive(session)
            refreshActiveSession()

            sessionReader.result = SessionReader.Result.Unknown
            refreshActiveSession()

            val storedSession = sessionStore.read().value

            assertEquals(session, storedSession)
        }

    @Test
    fun `given no cached active session and cannot determine new state, when called, session is still null`() =
        runTest {
            sessionStore.clear()
            sessionReader.result = SessionReader.Result.Unknown
            refreshActiveSession()

            val storedSession = sessionStore.read().value

            assertNull(storedSession)
        }
}
