package uk.gov.onelogin.criorchestrator.features.session.internal.network.activesession

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

sealed class ActiveSessionApiResponse {
    @Serializable
    @OptIn(ExperimentalSerializationApi::class)
    data class ActiveSessionSuccess(
        @JsonNames("sessionId")
        val sessionId: String,
        @JsonNames("redirectUri")
        val redirectUri: String? = null,
        @JsonNames("state")
        val state: String,
    ) : ActiveSessionApiResponse()
}
