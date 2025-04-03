package uk.gov.onelogin.criorchestrator.testwrapper.network

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
@OptIn(ExperimentalSerializationApi::class)
data class TokenResponse(
    @JsonNames("access_token")
    val accessToken: String,
    @JsonNames("expires_in")
    val expiresIn: Int,
)
