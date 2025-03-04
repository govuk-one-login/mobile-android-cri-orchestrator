package uk.gov.onelogin.criorchestrator.features.session.internalapi.domain

/**
 * Data class for storing the session data returned by the active session API call.
 *
 * @param [sessionId] The unique ID for the returned active session found for a given sub identifier.
 * @param [redirectUri] An optional registered client URI where app should attempt to direct the
 * user back to once ID check is complete.
 * @param [state] Cryptographically secure random string provided by IPV when session created and
 * stored in authSession table.
*/
data class Session(
    val sessionId: String,
    val redirectUri: String? = null,
    val state: String,
)
