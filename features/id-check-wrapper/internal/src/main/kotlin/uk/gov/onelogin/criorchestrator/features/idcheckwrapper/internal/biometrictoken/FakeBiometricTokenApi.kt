package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken

import dev.zacsweers.metro.Inject
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import uk.gov.android.network.api.v3.ApiResponse
import uk.gov.android.network.service.v2.NetworkServiceResponse
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.DocumentVariety

@Inject
class FakeBiometricTokenApi : BiometricApi {
    private val json: Json by lazy {
        Json {
            ignoreUnknownKeys = true
        }
    }

    override suspend fun getBiometricToken(
        sessionId: String,
        documentVariety: DocumentVariety,
    ): NetworkServiceResponse {
        val response =
            BiometricToken(
                accessToken = "SlAV32hkKG",
                opaqueId = "11111111-1111-1111-1111-111111111111",
            )
        val responseString = json.encodeToString(response)

        return ApiResponse.Success(body = responseString, status = 200)
    }
}

// This is to avoid having a json string because github has flagged "accessToken" and security issue
// Additionally, BiometricToken is not serializable in repositories library
@Serializable
private data class BiometricToken(
    val accessToken: String,
    val opaqueId: String,
)
