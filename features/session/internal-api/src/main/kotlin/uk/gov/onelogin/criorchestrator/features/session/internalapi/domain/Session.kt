package uk.gov.onelogin.criorchestrator.features.session.internalapi.domain

data class Session(
    val sessionId: String,
    val redirectUri: String? = null,
    val state: String,
)
