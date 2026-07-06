package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken

import uk.gov.android.network.api.v2.ApiResponse
import uk.gov.android.network.service.NetworkingException
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.DocumentVariety

fun interface BiometricApi {
    suspend fun getBiometricToken(
        sessionId: String,
        documentVariety: DocumentVariety,
    ): ApiResponse<String, NetworkingException>
}
