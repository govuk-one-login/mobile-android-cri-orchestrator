package uk.gov.onelogin.criorchestrator.features.session.internalapi.domain

import kotlinx.serialization.Serializable

@Serializable
data class Session(
    val sessionId: String,
    val redirectUri: String? = null,
    val state: String,
) {
    companion object;
}
