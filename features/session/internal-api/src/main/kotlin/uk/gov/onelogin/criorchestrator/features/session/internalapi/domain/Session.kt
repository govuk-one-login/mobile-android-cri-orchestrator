package uk.gov.onelogin.criorchestrator.features.session.internalapi.domain

/**
 * Stores the session information needed for the ID Check journey.
 * @param sessionId UUID that uniquely identifies the user's session.
 * @param redirectUri URI that the user is redirected to after the journey, composed of the active session API response
 * redirect URI and the response state as a query parameter.
 */
data class Session(
    val sessionId: String,
    val redirectUri: String? = null,
) {
    companion object;
}
