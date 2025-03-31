package uk.gov.onelogin.criorchestrator.features.session.internal.session

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import uk.gov.logging.testdouble.SystemLogger
import uk.gov.onelogin.criorchestrator.features.session.internal.network.InMemorySessionStore
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session

class InMemorySessionStoreTest {
    private val logger = SystemLogger()
    private val sessionStore = InMemorySessionStore(logger)
    private val initialSession = null

    @Test
    fun `session store returns null session if no session written`() {
        assertEquals(initialSession, sessionStore.read().value)
    }

    @Test
    fun `session store returns previously written session`() {
        val newSession =
            Session(
                sessionId = "test session ID",
                redirectUri = "test redirect URI",
                state = "test state",
            )

        sessionStore.write(newSession)
        assertEquals(newSession, sessionStore.read().value)
    }
}
