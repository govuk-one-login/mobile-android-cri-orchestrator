package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken.data

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

sealed class BiometricApiResponse {
    @Serializable
    @OptIn(ExperimentalSerializationApi::class)
    data class BiometricSuccess(
        @JsonNames("accessToken")
        val accessToken: String,
        @JsonNames("opaqueId")
        val opaqueId: String,
    ) : BiometricApiResponse()
}
