package uk.gov.onelogin.criorchestrator.features.session.internalapi.domain

/**
 * Stores the session information needed for the ID Check journey.
 * @param sessionId UUID that uniquely identifies the user's session.
 * @param redirectUri URI that the user is redirected to after the journey, composed of the active session API response
 * @param sessionState The known [State] of the session.
 *   This is distinct from the [redirectUri]'s 'state' query parameter.
 * redirect URI and the response state as a query parameter.
 */
data class Session(
    val sessionId: String,
    val redirectUri: String? = null,
    val sessionState: State = State.Created,
) {
    companion object;

    fun copyUpdateState(advance: State.() -> State) = copy(sessionState = sessionState.advance())

    /**
     * The known states of the session.
     */
    enum class State {
        /**
         * The session has created a session.
         *
         * The session is known as being 'active' which corresponds to the user still being able to select a document.
         *
         * The user hasn't selected a document or aborted the session yet.
         */
        Created,

        /**
         * The user has selected a document. In this state, the session is not 'active'.
         */
        DocumentSelected,

        /**
         * The user has aborted the session. In this state, the session is not 'active'.
         */
        Aborted,

        ;

        fun advanceAtLeastDocumentSelected(): State =
            when (this) {
                Created -> {
                    DocumentSelected
                }
                DocumentSelected,
                Aborted,
                -> {
                    // State is already at the minimum state
                    this
                }
            }

        fun advanceAtLeastAborted(): State = Aborted
    }
}
