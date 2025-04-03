package uk.gov.onelogin.criorchestrator.features.session.internalapi

import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session

fun Session.Companion.createTestInstance(
    sessionId: String = "test session ID",
    redirectUri: String = "test redirect URI",
    state: String = "test state",
) = Session(
    sessionId = sessionId,
    redirectUri = redirectUri,
    state = state,
)
