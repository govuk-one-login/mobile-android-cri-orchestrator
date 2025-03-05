package uk.gov.onelogin.criorchestrator.features.session.internal.network.response

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
        val redirectUri: String,
        @JsonNames("state")
        val state: String,
    ) : ActiveSessionApiResponse()
}
