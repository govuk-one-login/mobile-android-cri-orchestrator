package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken

import uk.gov.android.network.api.ApiResponse

fun interface BiometricApi {
    suspend fun getBiometricToken(
        sessionId: String,
        documentType: String,
    ): ApiResponse
}
