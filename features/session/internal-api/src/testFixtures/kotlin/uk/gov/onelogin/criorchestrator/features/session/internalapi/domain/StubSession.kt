package uk.gov.onelogin.criorchestrator.features.session.internalapi.domain

const val REDIRECT_URI = "https://example/redirect"

fun Session.Companion.createTestInstance(
    sessionId: String = "test-session-id",
    redirectUri: String? = null,
    sessionState: Session.State = Session.State.Created,
): Session =
    Session(
        sessionId = sessionId,
        redirectUri = redirectUri,
        sessionState = sessionState,
    )

fun Session.Companion.createDesktopAppDesktopInstance(): Session =
    Session.createTestInstance(
        redirectUri = null,
    )

fun Session.Companion.createMobileAppMobileInstance(
    redirectUri: String = "http://mam-redirect-uri?state=mock-state",
): Session =
    Session.createTestInstance(
        redirectUri = redirectUri,
    )
