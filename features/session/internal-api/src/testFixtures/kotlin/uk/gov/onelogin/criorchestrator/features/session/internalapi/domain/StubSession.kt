package uk.gov.onelogin.criorchestrator.features.session.internalapi.domain

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
