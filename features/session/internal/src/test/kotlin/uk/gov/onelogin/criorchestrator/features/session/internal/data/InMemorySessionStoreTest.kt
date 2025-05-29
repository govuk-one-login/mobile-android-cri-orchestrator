package uk.gov.onelogin.criorchestrator.features.session.internal.data

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNull
import uk.gov.logging.testdouble.SystemLogger
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.createTestInstance

class InMemorySessionStoreTest {
    private val logger = SystemLogger()
    private val sessionStore = InMemorySessionStore(logger)
    private val initialSession = null
    private val newSession = Session.createTestInstance()

    @Test
    fun `session store returns null session if no session written`() {
        assertEquals(initialSession, sessionStore.read().value)
    }

    @Test
    fun `session store returns previously written session`() {
        sessionStore.write(newSession)
        assertEquals(newSession, sessionStore.read().value)
    }

    @Test
    fun `when clear is called it deletes the stored session`() {
        sessionStore.write(newSession)
        sessionStore.clear()
        assertEquals(null, sessionStore.read().value)
    }

    @Test
    fun `given no stored session, when updateToAborted is called, it does nothing`() {
        sessionStore.clear()
        sessionStore.updateToAborted()
        assertNull(sessionStore.read().value)
    }

    @Test
    fun `given stored session, when updateToAborted is called it updates the stored session`() {
        sessionStore.write(newSession)
        sessionStore.updateToAborted()
        assertEquals(Session.State.Aborted, sessionStore.read().value?.sessionState)
    }

    @Test
    fun `given no stored session, when updateToDocumentSelected is called, it does nothing`() {
        sessionStore.clear()
        sessionStore.updateToDocumentSelected()
        assertNull(sessionStore.read().value)
    }

    @Test
    fun `given stored session, when updateToDocumentSelected is called it updates the stored session`() {
        sessionStore.write(newSession)
        sessionStore.updateToDocumentSelected()
        assertEquals(Session.State.DocumentSelected, sessionStore.read().value?.sessionState)
    }
}
