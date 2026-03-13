package uk.gov.onelogin.criorchestrator.features.session.internal.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNull
import uk.gov.logging.testdouble.SystemLogger
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.createTestInstance
import uk.gov.onelogin.criorchestrator.libraries.store.FakePersistentStore

class SessionStoreTest {
    private val logger = SystemLogger()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testScope = TestScope(UnconfinedTestDispatcher())
    private val store = FakePersistentStore<Session>()
    private val sessionStore =
        SessionStore(
            logger = logger,
            store = store,
        )

    private val session = Session.createTestInstance()

    @Test
    fun `given no stored session, when updateToAborted is called, it does nothing`() =
        testScope.runTest {
            sessionStore.clear()
            sessionStore.updateToAborted()
            assertNull(sessionStore.read().value)
        }

    @Test
    fun `given stored session, when updateToAborted is called it updates the stored session`() =
        testScope.runTest {
            sessionStore.write(session)
            sessionStore.updateToAborted()
            assertEquals(Session.State.Aborted, sessionStore.read().value?.sessionState)
        }

    @Test
    fun `given no stored session, when updateToDocumentSelected is called, it does nothing`() =
        testScope.runTest {
            sessionStore.clear()
            sessionStore.updateToDocumentSelected()
            assertNull(sessionStore.read().value)
        }

    @Test
    fun `given stored session, when updateToDocumentSelected is called it updates the stored session`() =
        testScope.runTest {
            sessionStore.write(session)
            sessionStore.updateToDocumentSelected()
            assertEquals(Session.State.DocumentSelected, sessionStore.read().value?.sessionState)
        }
}
