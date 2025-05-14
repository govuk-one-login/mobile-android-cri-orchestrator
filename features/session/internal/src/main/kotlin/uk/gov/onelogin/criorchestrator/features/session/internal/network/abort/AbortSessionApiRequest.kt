package uk.gov.onelogin.criorchestrator.features.session.internal.network.abort

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
@OptIn(ExperimentalSerializationApi::class)
data class AbortSessionApiRequest(
    @JsonNames("sessionId")
    val sessionId: String,
)
