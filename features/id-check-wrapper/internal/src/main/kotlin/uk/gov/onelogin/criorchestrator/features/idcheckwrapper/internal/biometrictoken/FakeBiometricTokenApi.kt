package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import uk.gov.android.network.api.ApiResponse
import javax.inject.Inject

class FakeBiometricTokenApi
    @Inject
    constructor() : BiometricApi {
        private val json: Json by lazy {
            Json {
                ignoreUnknownKeys = true
            }
        }

        override suspend fun getBiometricToken(
            sessionId: String,
            documentType: String,
        ): ApiResponse {
            val response = BiometricToken("SlAV32hkKG", "11111111-1111-1111-1111-111111111111")
            val responseString = json.encodeToString(response)

            return ApiResponse.Success<String>(
                responseString,
            )
        }
    }

// This is to avoid having string response because github has flagged "accessToken" and security issue
// Also BiometricToken is not serializable in repositories library
@Serializable
private data class BiometricToken(
    val accessToken: String,
    val opaqueId: String,
)
