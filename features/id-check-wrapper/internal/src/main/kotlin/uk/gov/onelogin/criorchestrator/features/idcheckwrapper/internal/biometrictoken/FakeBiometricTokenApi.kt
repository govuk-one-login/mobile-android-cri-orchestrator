package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken

import android.util.Log
import uk.gov.android.network.api.ApiResponse
import javax.inject.Inject

class FakeBiometricTokenApi
    @Inject
    constructor() : BiometricApi {
        override suspend fun getBiometricToken(
            sessionId: String,
            documentType: String,
        ): ApiResponse {
            Log.d(
                "FakeBiometricTokenApi",
                "getBiometricToken called with sessionId: $sessionId and documentType: $documentType",
            )

            return ApiResponse.Success<String>(
                """
                {
                    "accessToken": "SlAV32hkKG",
                    "opaqueId": "11111111-1111-1111-1111-111111111111"    
                }
                """.trimIndent(),
            )
        }
    }
