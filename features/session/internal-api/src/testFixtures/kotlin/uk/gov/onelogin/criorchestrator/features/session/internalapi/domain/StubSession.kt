package uk.gov.onelogin.criorchestrator.features.session.internalapi.domain

const val REDIRECT_URI = "https://example/redirect"

fun Session.Companion.createTestInstance(
    sessionId: String = "test-session-id",
    redirectUri: String? = null,
    state: String = "test-state",
): Session =
    Session(
        sessionId = sessionId,
        redirectUri = redirectUri,
        state = state,
    )
