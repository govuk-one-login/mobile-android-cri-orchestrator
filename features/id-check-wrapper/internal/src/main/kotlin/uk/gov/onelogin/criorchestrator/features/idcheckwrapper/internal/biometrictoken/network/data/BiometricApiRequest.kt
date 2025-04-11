package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.network.data

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

sealed class BiometricApiRequest {
    @Serializable
    @OptIn(ExperimentalSerializationApi::class)
    data class BiometricRequest(
        @JsonNames("sessionId")
        val sessionId: String,
        @JsonNames("documentType")
        val documentType: String,
    ) : BiometricApiRequest()
}