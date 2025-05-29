package uk.gov.onelogin.criorchestrator.features.session.internalapi.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SessionTest {
    private val defaultSession =
        Session(
            sessionId = "sessionId",
            redirectUri = "redirectUri",
        )

    @Test
    fun `default session state`() {
        assertEquals(
            Session.State.Created,
            defaultSession.sessionState,
        )
    }

    @Test
    fun `copy advance state to aborted`() {
        val newSession = defaultSession.copyUpdateState { advanceAtLeastAborted() }
        assertEquals(
            Session.State.Aborted,
            newSession.sessionState,
        )
    }

    @Test
    fun `copy advance state to document selected`() {
        val newSession = defaultSession.copyUpdateState { advanceAtLeastDocumentSelected() }
        assertEquals(
            Session.State.DocumentSelected,
            newSession.sessionState,
        )
    }

    @Test
    fun `copy advance state from document selected to aborted`() {
        val newSession =
            defaultSession
                .copyUpdateState { advanceAtLeastDocumentSelected() }
                .copyUpdateState { advanceAtLeastAborted() }
        assertEquals(
            Session.State.Aborted,
            newSession.sessionState,
        )
    }

    @Test
    fun `copy advance state from aborted to document selected, remains at aborted`() {
        val newSession =
            defaultSession
                .copyUpdateState { advanceAtLeastAborted() }
                .copyUpdateState { advanceAtLeastDocumentSelected() }
        assertEquals(
            Session.State.Aborted,
            newSession.sessionState,
        )
    }
}
