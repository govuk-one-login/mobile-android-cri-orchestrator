package uk.gov.onelogin.criorchestrator.features.session.internal.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNull
import uk.gov.logging.testdouble.SystemLogger
import uk.gov.onelogin.criorchestrator.features.session.internal.FakeSecureStoreAsyncV2
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.createTestInstance

class PersistentSessionStoreTest {
    private val logger = SystemLogger()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testScope = TestScope(UnconfinedTestDispatcher())
    private val secureStore = FakeSecureStoreAsyncV2()
    private val sessionStore = createStore()

    private fun createStore() =
        PersistentSessionStore(
            logger = logger,
            secureStore = secureStore,
            coroutineScope = testScope,
        )

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

    @Test
    fun `given stored session, new store instance reads the stored value`() {
        sessionStore.write(newSession)

        val newStore = createStore()
        assertEquals(newSession, newStore.read().value)
    }

    @Test
    fun `given secure store has data but throws on load, it deletes all data and returns null`() {
        sessionStore.write(newSession)
        assertTrue(secureStore.hasData())

        secureStore.throwOnRetrieve = true
        val store = createStore()

        assertNull(store.read().value)
        assertFalse(secureStore.hasData())
    }

    @Test
    fun `given secure store throws on upsert, when write called, value is not updated`() {
        secureStore.throwOnUpsert = true

        sessionStore.write(newSession)

        assertEquals(initialSession, sessionStore.read().value)
    }

    @Test
    fun `given secure store throws on delete, when clear called, value is not updated`() {
        sessionStore.write(newSession)
        secureStore.throwOnDelete = true

        sessionStore.clear()

        assertEquals(newSession, sessionStore.read().value)
    }

    @Test
    fun `given secure store has data but throws on load and deleteAll also fails, returns null`() {
        sessionStore.write(newSession)
        assertTrue(secureStore.hasData())

        secureStore.throwOnRetrieve = true
        secureStore.throwOnDeleteAll = true
        val store = createStore()

        assertNull(store.read().value)
    }
}
