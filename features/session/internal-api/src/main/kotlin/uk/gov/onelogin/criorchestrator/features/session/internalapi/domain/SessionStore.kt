package uk.gov.onelogin.criorchestrator.features.session.internalapi.domain

import kotlinx.coroutines.flow.StateFlow

/**
 * Stores and retrieves the known state of the user's ID Check session (see [Session]).
 *
 * Implementations may or may not persist the data.
 */
interface SessionStore {
    /**
     * Get the last known state of the user's ID Check session.
     */
    fun read(): StateFlow<Session?>

    /**
     * Store the user's ID Check session.
     */
    fun write(value: Session)

    /**
     * Clear the session.
     *
     * After calling, [read] emits `null`.
     */
    fun clear()

    /**
     * Update the stored [Session.sessionState] to at least [Session.State.DocumentSelected].
     *
     * If there is no [Session] or [Session.sessionState] has already advanced to or beyond
     * this state, this function does nothing.
     */
    fun updateToDocumentSelected()

    /**
     * Update the stored [Session.sessionState] to at least [Session.State.Aborted].
     *
     * If there is no [Session] or [Session.sessionState] has already advanced to or beyond
     * this state, this function does nothing.
     */
    fun updateToAborted()
}
